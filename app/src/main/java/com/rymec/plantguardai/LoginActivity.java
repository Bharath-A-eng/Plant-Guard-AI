package com.rymec.plantguardai;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.tasks.Task;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "PlantGuardGoogle";

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerText;
    private TextView forgotPasswordText;

    private FirebaseAuth auth;

    private LinearLayout googleSignInButton;
    private GoogleSignInClient googleSignInClient;


    // =========================================================
    // GOOGLE SIGN-IN RESULT
    // =========================================================

    private final ActivityResultLauncher<Intent> googleSignInLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                        Log.d(
                                TAG,
                                "Google result code: " + result.getResultCode()
                        );

                        if (result.getData() == null) {

                            Log.e(
                                    TAG,
                                    "Google result data is NULL"
                            );

                            Toast.makeText(
                                    LoginActivity.this,
                                    "Google Sign-In returned no data",
                                    Toast.LENGTH_LONG
                            ).show();

                            return;
                        }


                        Task<GoogleSignInAccount> task =
                                GoogleSignIn.getSignedInAccountFromIntent(
                                        result.getData()
                                );


                        try {

                            GoogleSignInAccount account =
                                    task.getResult(ApiException.class);


                            if (account == null) {

                                Log.e(
                                        TAG,
                                        "Google account is NULL"
                                );

                                Toast.makeText(
                                        LoginActivity.this,
                                        "Google account is null",
                                        Toast.LENGTH_LONG
                                ).show();

                                return;
                            }


                            Log.d(
                                    TAG,
                                    "Google account received"
                            );

                            Log.d(
                                    TAG,
                                    "Google email: " + account.getEmail()
                            );


                            String idToken = account.getIdToken();


                            Log.d(
                                    TAG,
                                    "ID Token exists: " +
                                            (idToken != null)
                            );


                            if (idToken == null || idToken.isEmpty()) {

                                Log.e(
                                        TAG,
                                        "Google ID Token is NULL or empty"
                                );

                                Toast.makeText(
                                        LoginActivity.this,
                                        "Google ID token is missing",
                                        Toast.LENGTH_LONG
                                ).show();

                                return;
                            }


                            // Send Google token to Firebase
                            firebaseAuthWithGoogle(idToken);


                        } catch (ApiException e) {

                            Log.e(
                                    TAG,
                                    "Google Sign-In failed. Status code: "
                                            + e.getStatusCode(),
                                    e
                            );


                            Toast.makeText(
                                    LoginActivity.this,
                                    "Google Sign-In failed: "
                                            + e.getStatusCode(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            );


    // =========================================================
    // ON CREATE
    // =========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LocaleHelper.loadLocale(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        // Firebase
        auth = FirebaseAuth.getInstance();


        // =====================================================
        // GOOGLE SIGN-IN CONFIGURATION
        // =====================================================

        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(
                        GoogleSignInOptions.DEFAULT_SIGN_IN
                )
                        .requestIdToken(
                                getString(
                                        R.string.default_web_client_id
                                )
                        )
                        .requestEmail()
                        .build();


        googleSignInClient =
                GoogleSignIn.getClient(
                        this,
                        gso
                );


        // =====================================================
        // FIND VIEWS
        // =====================================================

        emailEditText =
                findViewById(R.id.emailEditText);

        passwordEditText =
                findViewById(R.id.passwordEditText);

        loginButton =
                findViewById(R.id.loginButton);

        registerText =
                findViewById(R.id.registerText);

        forgotPasswordText =
                findViewById(R.id.forgotPasswordText);

        googleSignInButton =
                findViewById(R.id.googleSignInButton);


        // =====================================================
        // EMAIL LOGIN
        // =====================================================

        loginButton.setOnClickListener(
                v -> loginUser()
        );


        // =====================================================
        // GOOGLE LOGIN
        // =====================================================

        googleSignInButton.setOnClickListener(v -> {

            Log.d(
                    TAG,
                    "Google Sign-In button clicked"
            );


            Intent signInIntent =
                    googleSignInClient.getSignInIntent();


            googleSignInLauncher.launch(
                    signInIntent
            );
        });


        // =====================================================
        // REGISTER
        // =====================================================

        registerText.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            LoginActivity.this,
                            RegisterActivity.class
                    );

            startActivity(intent);
        });


        // =====================================================
        // FORGOT PASSWORD
        // =====================================================

        forgotPasswordText.setOnClickListener(v -> {

            String email =
                    emailEditText.getText()
                            .toString()
                            .trim();


            if (email.isEmpty()) {

                emailEditText.setError(
                        getString(
                                R.string.enter_email_first
                        )
                );

                emailEditText.requestFocus();

                return;
            }


            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            Toast.makeText(
                                    LoginActivity.this,
                                    getString(
                                            R.string.password_reset_sent
                                    ),
                                    Toast.LENGTH_LONG
                            ).show();

                        } else {

                            String error =
                                    task.getException() != null
                                            ? task.getException()
                                            .getMessage()
                                            : "Unknown error";


                            Log.e(
                                    TAG,
                                    "Password reset failed: "
                                            + error
                            );


                            Toast.makeText(
                                    LoginActivity.this,
                                    error,
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    });
        });
    }


    // =========================================================
    // EMAIL LOGIN METHOD
    // =========================================================

    private void loginUser() {

        String email =
                emailEditText.getText()
                        .toString()
                        .trim();


        String password =
                passwordEditText.getText()
                        .toString()
                        .trim();


        // Check email
        if (email.isEmpty()) {

            emailEditText.setError(
                    getString(
                            R.string.enter_email
                    )
            );

            emailEditText.requestFocus();

            return;
        }


        // Validate email
        if (!Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()) {

            emailEditText.setError(
                    getString(
                            R.string.enter_valid_email
                    )
            );

            emailEditText.requestFocus();

            return;
        }


        // Check password
        if (password.isEmpty()) {

            passwordEditText.setError(
                    getString(
                            R.string.enter_password
                    )
            );

            passwordEditText.requestFocus();

            return;
        }


        loginButton.setEnabled(false);


        auth.signInWithEmailAndPassword(
                        email,
                        password
                )
                .addOnCompleteListener(task -> {

                    loginButton.setEnabled(true);


                    if (task.isSuccessful()) {

                        // =================================================
                        // GET LOGGED-IN USER NAME
                        // =================================================

                        FirebaseUser user = auth.getCurrentUser();

                        String userName = "User";

                        if (user != null) {

                            if (user.getDisplayName() != null
                                    && !user.getDisplayName().trim().isEmpty()) {

                                userName =
                                        user.getDisplayName().trim();

                            }
                        }


                        // Save the logged-in user's name
                        getSharedPreferences(
                                "PlantGuardPrefs",
                                MODE_PRIVATE
                        )
                                .edit()
                                .putString(
                                        "user_name",
                                        userName
                                )
                                .apply();


                        Toast.makeText(
                                LoginActivity.this,
                                "Welcome " + userName,
                                Toast.LENGTH_SHORT
                        ).show();


                        openMainActivity();


                    } else {

                        String error =
                                task.getException() != null
                                        ? task.getException()
                                        .getMessage()
                                        : "Login failed";


                        Toast.makeText(
                                LoginActivity.this,
                                error,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }


    // =========================================================
    // FIREBASE GOOGLE AUTHENTICATION
    // =========================================================

    private void firebaseAuthWithGoogle(
            String idToken
    ) {

        Log.d(
                TAG,
                "Starting Firebase authentication with Google"
        );


        AuthCredential credential =
                GoogleAuthProvider.getCredential(
                        idToken,
                        null
                );


        auth.signInWithCredential(
                        credential
                )
                .addOnCompleteListener(
                        this,
                        task -> {

                            if (task.isSuccessful()) {

                                Log.d(
                                        TAG,
                                        "Firebase Google authentication successful"
                                );


                                // =================================================
                                // GET GOOGLE USER NAME
                                // =================================================

                                FirebaseUser user =
                                        auth.getCurrentUser();

                                String userName = "User";

                                if (user != null) {

                                    if (user.getDisplayName() != null
                                            && !user.getDisplayName().trim().isEmpty()) {

                                        userName =
                                                user.getDisplayName().trim();

                                    }
                                }


                                // Save the logged-in user's name
                                getSharedPreferences(
                                        "PlantGuardPrefs",
                                        MODE_PRIVATE
                                )
                                        .edit()
                                        .putString(
                                                "user_name",
                                                userName
                                        )
                                        .apply();


                                Toast.makeText(
                                        LoginActivity.this,
                                        "Welcome " + userName,
                                        Toast.LENGTH_SHORT
                                ).show();


                                openMainActivity();


                            } else {

                                String error =
                                        task.getException() != null
                                                ? task.getException()
                                                .getMessage()
                                                : "Firebase authentication failed";


                                Log.e(
                                        TAG,
                                        "Firebase Google authentication failed: "
                                                + error,
                                        task.getException()
                                );


                                Toast.makeText(
                                        LoginActivity.this,
                                        "Firebase login failed: "
                                                + error,
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );
    }


    // =========================================================
    // OPEN MAIN ACTIVITY
    // =========================================================

    private void openMainActivity() {

        Intent intent =
                new Intent(
                        LoginActivity.this,
                        MainActivity.class
                );


        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
        );


        startActivity(intent);

        finish();
    }
}