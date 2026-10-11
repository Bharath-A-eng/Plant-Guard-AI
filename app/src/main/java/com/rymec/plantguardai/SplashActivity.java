package com.rymec.plantguardai;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ImageView logoImage;
    private TextView appName;
    private TextView tagLine;
    private LottieAnimationView lottieAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LocaleHelper.loadLocale(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        logoImage = findViewById(R.id.logoImage);
        appName = findViewById(R.id.appName);
        tagLine = findViewById(R.id.tagLine);
        lottieAnimation = findViewById(R.id.lottieAnimation);

        // Animations
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);

        logoImage.startAnimation(scaleUp);
        appName.startAnimation(fadeIn);
        tagLine.startAnimation(fadeIn);

        lottieAnimation.playAnimation();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            FirebaseUser user = mAuth.getCurrentUser();

            Intent intent;

            if (user != null) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }

            startActivity(intent);

            overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
            );

            finish();

        }, 3000);
    }
}