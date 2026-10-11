package com.rymec.plantguardai;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // =========================================================
    // NOTIFICATION PERMISSION
    // =========================================================

    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1001;


    // =========================================================
    // SOIL MOISTURE THRESHOLD
    // =========================================================
    // Notification will be shown only when soil moisture
    // drops BELOW this value.
    // =========================================================

    private static final int SOIL_MOISTURE_THRESHOLD = 50;


    // =========================================================
    // NOTIFICATION PREFERENCES
    // =========================================================

    private static final String NOTIFICATION_PREFS =
            "PlantGuardNotificationPrefs";

    private static final String PLANT1_NOTIFIED =
            "plant1_notified";

    private static final String PLANT2_NOTIFIED =
            "plant2_notified";


    private SharedPreferences notificationPreferences;


    // =========================================================
    // PLANT 1
    // =========================================================

    private ImageView imgPlant1;

    private TextView txtTemperature1;
    private TextView txtHumidity1;
    private TextView txtSoilMoisture1;
    private TextView txtLightLevel1;
    private TextView txtPumpStatus1;

    private Switch switchPump1;
    private Spinner spinnerPlant1;


    // =========================================================
    // PLANT 2
    // =========================================================

    private ImageView imgPlant2;

    private TextView txtTemperature2;
    private TextView txtHumidity2;
    private TextView txtSoilMoisture2;
    private TextView txtLightLevel2;
    private TextView txtPumpStatus2;

    private Switch switchPump2;
    private Spinner spinnerPlant2;


    // =========================================================
    // BUTTONS
    // =========================================================

    private Button btnScan;
    private Button btnHistory;
    private Button btnSettings;
    private Button btnLogout;


    // =========================================================
    // PLANT LIST
    // =========================================================

    private ArrayList<PlantModel> plantList;


    // =========================================================
    // SELECTED PLANTS
    // =========================================================

    private String selectedPlant1 = "Apple";
    private String selectedPlant2 = "Tomato";


    // =========================================================
    // ACTIVITY CREATED
    // =========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // =====================================================
        // LOAD LANGUAGE
        // =====================================================

        LocaleHelper.loadLocale(this);

        super.onCreate(savedInstanceState);


        // =====================================================
        // CHECK LOGIN
        // =====================================================

        FirebaseUser user =
                FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {

            startActivity(
                    new Intent(
                            MainActivity.this,
                            LoginActivity.class
                    )
            );

            finish();
            return;
        }


        // =====================================================
        // SET MAIN LAYOUT
        // =====================================================

        setContentView(R.layout.activity_main);


        // =====================================================
        // CREATE NOTIFICATION CHANNEL
        // =====================================================

        NotificationHelper.createNotificationChannel(
                MainActivity.this
        );


        // =====================================================
        // LOAD NOTIFICATION PREFERENCES
        // =====================================================

        notificationPreferences =
                getSharedPreferences(
                        NOTIFICATION_PREFS,
                        MODE_PRIVATE
                );


        // =====================================================
        // REQUEST NOTIFICATION PERMISSION
        // Android 13+ only
        // =====================================================

        requestNotificationPermission();


        // =====================================================
        // GET USER NAME
        // =====================================================

        String name = user.getDisplayName();

        if (name == null || name.trim().isEmpty()) {

            if (user.getEmail() != null &&
                    !user.getEmail().isEmpty()) {

                String email = user.getEmail();

                int atPosition = email.indexOf("@");

                if (atPosition > 0) {

                    name = email.substring(0, atPosition);

                } else {

                    name = "User";
                }

            } else {

                name = "User";
            }
        }


        // =====================================================
        // WELCOME MESSAGE
        // =====================================================

        if (savedInstanceState == null) {

            Toast.makeText(
                    this,
                    getString(
                            R.string.welcome_user,
                            name
                    ),
                    Toast.LENGTH_SHORT
            ).show();
        }


        // =====================================================
        // FIND PLANT 1 VIEWS
        // =====================================================

        imgPlant1 =
                findViewById(R.id.imgPlant1);

        spinnerPlant1 =
                findViewById(R.id.spinnerPlant1);

        txtTemperature1 =
                findViewById(R.id.txtTemperature1);

        txtHumidity1 =
                findViewById(R.id.txtHumidity1);

        txtSoilMoisture1 =
                findViewById(R.id.txtSoilMoisture1);

        txtLightLevel1 =
                findViewById(R.id.txtLightLevel1);

        txtPumpStatus1 =
                findViewById(R.id.txtPumpStatus1);

        switchPump1 =
                findViewById(R.id.switchPump1);


        // =====================================================
        // FIND PLANT 2 VIEWS
        // =====================================================

        imgPlant2 =
                findViewById(R.id.imgPlant2);

        spinnerPlant2 =
                findViewById(R.id.spinnerPlant2);

        txtTemperature2 =
                findViewById(R.id.txtTemperature2);

        txtHumidity2 =
                findViewById(R.id.txtHumidity2);

        txtSoilMoisture2 =
                findViewById(R.id.txtSoilMoisture2);

        txtLightLevel2 =
                findViewById(R.id.txtLightLevel2);

        txtPumpStatus2 =
                findViewById(R.id.txtPumpStatus2);

        switchPump2 =
                findViewById(R.id.switchPump2);


        // =====================================================
        // FIND BUTTONS
        // =====================================================

        btnScan =
                findViewById(R.id.btnScan);

        btnHistory =
                findViewById(R.id.btnHistory);

        btnSettings =
                findViewById(R.id.btnSettings);

        btnLogout =
                findViewById(R.id.btnLogout);


        // =====================================================
        // CREATE PLANT LIST
        // =====================================================

        createPlantList();


        // =====================================================
        // SETUP PLANT SPINNERS
        // =====================================================

        setupPlantSpinners();


        // =====================================================
        // INITIAL PLANT VALUES
        // =====================================================

        updatePlant1Environment();
        updatePlant2Environment();


        // =====================================================
        // PLANT 1 PUMP SWITCH
        // =====================================================

        switchPump1.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(
                            CompoundButton buttonView,
                            boolean isChecked
                    ) {

                        if (isChecked) {

                            txtPumpStatus1.setText(
                                    getString(R.string.on)
                            );

                            txtTemperature1.setText(
                                    getString(
                                            R.string.temperature_low
                                    )
                            );

                            txtHumidity1.setText(
                                    getString(
                                            R.string.humidity_high
                                    )
                            );

                            txtSoilMoisture1.setText(
                                    getString(
                                            R.string.soil_high
                                    )
                            );

                            txtLightLevel1.setText(
                                    getString(R.string.low)
                            );

                            // ---------------------------------
                            // Pump is ON.
                            // No thirsty notification.
                            // ---------------------------------

                            resetPlant1NotificationState();

                        } else {

                            txtPumpStatus1.setText(
                                    getString(R.string.off)
                            );

                            updatePlant1Environment();
                        }
                    }
                }
        );


        // =====================================================
        // PLANT 2 PUMP SWITCH
        // =====================================================

        switchPump2.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(
                            CompoundButton buttonView,
                            boolean isChecked
                    ) {

                        if (isChecked) {

                            txtPumpStatus2.setText(
                                    getString(R.string.on)
                            );

                            txtTemperature2.setText(
                                    getString(
                                            R.string.temperature_low
                                    )
                            );

                            txtHumidity2.setText(
                                    getString(
                                            R.string.humidity_high
                                    )
                            );

                            txtSoilMoisture2.setText(
                                    getString(
                                            R.string.soil_high
                                    )
                            );

                            txtLightLevel2.setText(
                                    getString(R.string.low)
                            );

                            // ---------------------------------
                            // Pump is ON.
                            // No thirsty notification.
                            // ---------------------------------

                            resetPlant2NotificationState();

                        } else {

                            txtPumpStatus2.setText(
                                    getString(R.string.off)
                            );

                            updatePlant2Environment();
                        }
                    }
                }
        );


        // =====================================================
        // SCAN BUTTON
        // =====================================================

        btnScan.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            ScanActivity.class
                    );

            startActivity(intent);
        });


        // =====================================================
        // HISTORY BUTTON
        // =====================================================

        btnHistory.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            HistoryActivity.class
                    );

            startActivity(intent);
        });


        // =====================================================
        // SETTINGS BUTTON
        // =====================================================

        btnSettings.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            SettingsActivity.class
                    );

            startActivity(intent);
        });


        // =====================================================
        // LOGOUT BUTTON
        // =====================================================

        btnLogout.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Toast.makeText(
                    MainActivity.this,
                    getString(R.string.logged_out),
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            LoginActivity.class
                    );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);

            finish();
        });
    }


    // =========================================================
    // REQUEST NOTIFICATION PERMISSION
    // =========================================================

    private void requestNotificationPermission() {

        // Android 12 and below do not need runtime
        // notification permission.

        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                        new String[]{
                                Manifest.permission.POST_NOTIFICATIONS
                        },
                        NOTIFICATION_PERMISSION_REQUEST_CODE
                );
            }
        }
    }


    // =========================================================
    // NOTIFICATION PERMISSION RESULT
    // =========================================================

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults
    ) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );


        if (requestCode ==
                NOTIFICATION_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 &&
                    grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED) {

                // ---------------------------------------------
                // Permission granted.
                // Check both plants against the threshold.
                // ---------------------------------------------

                checkPlant1MoistureNotification();
                checkPlant2MoistureNotification();

            } else {

                Toast.makeText(
                        MainActivity.this,
                        "Notification permission denied",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }


    // =========================================================
    // CHECK PLANT 1 MOISTURE
    // =========================================================

    private void checkPlant1MoistureNotification() {

        // -----------------------------------------------------
        // Do not notify while pump is running.
        // -----------------------------------------------------

        if (switchPump1 != null &&
                switchPump1.isChecked()) {

            return;
        }


        int moisture =
                getPlantSoilMoisture(selectedPlant1);


        // -----------------------------------------------------
        // BELOW THRESHOLD
        // -----------------------------------------------------

        if (moisture < SOIL_MOISTURE_THRESHOLD) {

            boolean alreadyNotified =
                    notificationPreferences.getBoolean(
                            PLANT1_NOTIFIED,
                            false
                    );


            // -------------------------------------------------
            // Only send notification once while the plant
            // remains below the threshold.
            // -------------------------------------------------

            if (!alreadyNotified) {

                showThirstyNotification(
                        selectedPlant1,
                        moisture
                );

                notificationPreferences
                        .edit()
                        .putBoolean(
                                PLANT1_NOTIFIED,
                                true
                        )
                        .apply();
            }

        } else {

            // -------------------------------------------------
            // Moisture has recovered.
            // Reset notification state so another notification
            // can be sent if it drops below 50% again.
            // -------------------------------------------------

            resetPlant1NotificationState();
        }
    }


    // =========================================================
    // CHECK PLANT 2 MOISTURE
    // =========================================================

    private void checkPlant2MoistureNotification() {

        // -----------------------------------------------------
        // Do not notify while pump is running.
        // -----------------------------------------------------

        if (switchPump2 != null &&
                switchPump2.isChecked()) {

            return;
        }


        int moisture =
                getPlantSoilMoisture(selectedPlant2);


        // -----------------------------------------------------
        // BELOW THRESHOLD
        // -----------------------------------------------------

        if (moisture < SOIL_MOISTURE_THRESHOLD) {

            boolean alreadyNotified =
                    notificationPreferences.getBoolean(
                            PLANT2_NOTIFIED,
                            false
                    );


            // -------------------------------------------------
            // Only send notification once while the plant
            // remains below the threshold.
            // -------------------------------------------------

            if (!alreadyNotified) {

                showThirstyNotification(
                        selectedPlant2,
                        moisture
                );

                notificationPreferences
                        .edit()
                        .putBoolean(
                                PLANT2_NOTIFIED,
                                true
                        )
                        .apply();
            }

        } else {

            // -------------------------------------------------
            // Moisture has recovered.
            // -------------------------------------------------

            resetPlant2NotificationState();
        }
    }


    // =========================================================
    // SHOW THIRSTY NOTIFICATION
    // =========================================================

    private void showThirstyNotification(
            String plantName,
            int moisture
    ) {

        // -----------------------------------------------------
        // Android 13+ permission check
        // -----------------------------------------------------

        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
        }


        // -----------------------------------------------------
        // Notification message
        // -----------------------------------------------------

        String message =
                "Soil moisture is only " +
                        moisture +
                        "%. Please provide water.";


        // -----------------------------------------------------
        // Send notification
        // -----------------------------------------------------

        NotificationHelper.showThirstyNotification(
                MainActivity.this,
                plantName,
                String.valueOf(moisture) + "%"
        );
    }


    // =========================================================
    // RESET PLANT 1 NOTIFICATION STATE
    // =========================================================

    private void resetPlant1NotificationState() {

        if (notificationPreferences != null) {

            notificationPreferences
                    .edit()
                    .putBoolean(
                            PLANT1_NOTIFIED,
                            false
                    )
                    .apply();
        }
    }


    // =========================================================
    // RESET PLANT 2 NOTIFICATION STATE
    // =========================================================

    private void resetPlant2NotificationState() {

        if (notificationPreferences != null) {

            notificationPreferences
                    .edit()
                    .putBoolean(
                            PLANT2_NOTIFIED,
                            false
                    )
                    .apply();
        }
    }


    // =========================================================
    // GET PLANT SOIL MOISTURE
    // =========================================================

    private int getPlantSoilMoisture(String plantId) {

        switch (plantId) {

            case "Apple":
                return 61;

            case "Blueberry":
                return 66;

            case "Cherry":
                return 58;

            case "Corn":
                return 55;

            case "Grape":
                return 52;

            case "Orange":
                return 63;

            case "Peach":
                return 57;

            case "Bell Pepper":
                return 60;

            case "Potato":
                return 68;

            case "Raspberry":
                return 64;

            case "Soybean":
                return 59;

            case "Squash":
                return 62;

            case "Strawberry":
                return 70;

            case "Tomato":
                return 43;

            default:
                return 60;
        }
    }


    // =========================================================
    // CREATE PLANT LIST
    // =========================================================

    private void createPlantList() {

        plantList = new ArrayList<>();

        plantList.add(new PlantModel(
                "Apple",
                getString(R.string.apple),
                getString(R.string.apple_diseases),
                R.drawable.apple
        ));

        plantList.add(new PlantModel(
                "Blueberry",
                getString(R.string.blueberry),
                getString(R.string.blueberry_diseases),
                R.drawable.blueberry
        ));

        plantList.add(new PlantModel(
                "Cherry",
                getString(R.string.cherry),
                getString(R.string.cherry_diseases),
                R.drawable.cherry
        ));

        plantList.add(new PlantModel(
                "Corn",
                getString(R.string.corn),
                getString(R.string.corn_diseases),
                R.drawable.corn
        ));

        plantList.add(new PlantModel(
                "Grape",
                getString(R.string.grape),
                getString(R.string.grape_diseases),
                R.drawable.grape
        ));

        plantList.add(new PlantModel(
                "Orange",
                getString(R.string.orange),
                getString(R.string.orange_diseases),
                R.drawable.orange
        ));

        plantList.add(new PlantModel(
                "Peach",
                getString(R.string.peach),
                getString(R.string.peach_diseases),
                R.drawable.peach
        ));

        plantList.add(new PlantModel(
                "Bell Pepper",
                getString(R.string.bell_pepper),
                getString(R.string.pepper_diseases),
                R.drawable.pepper
        ));

        plantList.add(new PlantModel(
                "Potato",
                getString(R.string.potato),
                getString(R.string.potato_diseases),
                R.drawable.potato
        ));

        plantList.add(new PlantModel(
                "Raspberry",
                getString(R.string.raspberry),
                getString(R.string.raspberry_diseases),
                R.drawable.raspberry
        ));

        plantList.add(new PlantModel(
                "Soybean",
                getString(R.string.soybean),
                getString(R.string.soybean_diseases),
                R.drawable.soybean
        ));

        plantList.add(new PlantModel(
                "Squash",
                getString(R.string.squash),
                getString(R.string.squash_diseases),
                R.drawable.squash
        ));

        plantList.add(new PlantModel(
                "Strawberry",
                getString(R.string.strawberry),
                getString(R.string.strawberry_diseases),
                R.drawable.strawberry
        ));

        plantList.add(new PlantModel(
                "Tomato",
                getString(R.string.tomato),
                getString(R.string.tomato_diseases),
                R.drawable.tomato
        ));
    }


    // =========================================================
    // SETUP BOTH SPINNERS
    // =========================================================

    private void setupPlantSpinners() {

        ArrayList<String> plantNames =
                new ArrayList<>();

        for (PlantModel plant : plantList) {

            plantNames.add(
                    plant.getPlantName()
            );
        }


        // =====================================================
        // CREATE ADAPTER
        // =====================================================

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        plantNames
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );


        // =====================================================
        // PLANT 1
        // =====================================================

        spinnerPlant1.setAdapter(adapter);

        spinnerPlant1.setSelection(
                findPlantPosition(selectedPlant1)
        );

        spinnerPlant1.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id
                    ) {

                        PlantModel selectedPlant =
                                plantList.get(position);

                        selectedPlant1 =
                                selectedPlant.getPlantId();

                        updatePlant1Environment();
                    }


                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent
                    ) {
                    }
                }
        );


        // =====================================================
        // PLANT 2
        // =====================================================

        spinnerPlant2.setAdapter(adapter);

        spinnerPlant2.setSelection(
                findPlantPosition(selectedPlant2)
        );

        spinnerPlant2.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id
                    ) {

                        PlantModel selectedPlant =
                                plantList.get(position);

                        selectedPlant2 =
                                selectedPlant.getPlantId();

                        updatePlant2Environment();
                    }


                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent
                    ) {
                    }
                }
        );
    }


    // =========================================================
    // FIND PLANT POSITION
    // =========================================================

    private int findPlantPosition(String plantId) {

        for (int i = 0; i < plantList.size(); i++) {

            if (plantList
                    .get(i)
                    .getPlantId()
                    .equals(plantId)) {

                return i;
            }
        }

        return 0;
    }


    // =========================================================
    // GET PLANT IMAGE
    // =========================================================

    private int getPlantImage(String plantId) {

        switch (plantId) {

            case "Apple":
                return R.drawable.apple;

            case "Blueberry":
                return R.drawable.blueberry;

            case "Cherry":
                return R.drawable.cherry;

            case "Corn":
                return R.drawable.corn;

            case "Grape":
                return R.drawable.grape;

            case "Orange":
                return R.drawable.orange;

            case "Peach":
                return R.drawable.peach;

            case "Bell Pepper":
                return R.drawable.pepper;

            case "Potato":
                return R.drawable.potato;

            case "Raspberry":
                return R.drawable.raspberry;

            case "Soybean":
                return R.drawable.soybean;

            case "Squash":
                return R.drawable.squash;

            case "Strawberry":
                return R.drawable.strawberry;

            case "Tomato":
                return R.drawable.tomato;

            default:
                return R.drawable.apple;
        }
    }


    // =========================================================
    // UPDATE PLANT 1 IMAGE
    // =========================================================

    private void updatePlant1Image() {

        imgPlant1.setImageResource(
                getPlantImage(selectedPlant1)
        );
    }


    // =========================================================
    // UPDATE PLANT 2 IMAGE
    // =========================================================

    private void updatePlant2Image() {

        imgPlant2.setImageResource(
                getPlantImage(selectedPlant2)
        );
    }


    // =========================================================
    // UPDATE PLANT 1 ENVIRONMENT
    // =========================================================

    private void updatePlant1Environment() {

        updatePlant1Image();


        switch (selectedPlant1) {

            case "Apple":

                txtTemperature1.setText("28.4°C");
                txtHumidity1.setText("72%");
                txtSoilMoisture1.setText("61%");
                txtLightLevel1.setText(
                        getString(R.string.good)
                );

                break;


            case "Blueberry":

                txtTemperature1.setText("24.5°C");
                txtHumidity1.setText("78%");
                txtSoilMoisture1.setText("66%");
                txtLightLevel1.setText(
                        getString(R.string.good)
                );

                break;


            case "Cherry":

                txtTemperature1.setText("23.8°C");
                txtHumidity1.setText("70%");
                txtSoilMoisture1.setText("58%");
                txtLightLevel1.setText(
                        getString(R.string.good)
                );

                break;


            case "Corn":

                txtTemperature1.setText("29.2°C");
                txtHumidity1.setText("65%");
                txtSoilMoisture1.setText("55%");
                txtLightLevel1.setText(
                        getString(R.string.good)
                );

                break;


            case "Grape":

                txtTemperature1.setText("26.7°C");
                txtHumidity1.setText("68%");
                txtSoilMoisture1.setText("52%");
                txtLightLevel1.setText(
                        getString(R.string.good)
                );

                break;


            case "Orange":

                txtTemperature1.setText("27.5°C");
                txtHumidity1.setText("74%");
                txtSoilMoisture1.setText("63%");
                txtLightLevel1.setText(
                        getString(R.string.good)
                );

                break;


            case "Peach":

                txtTemperature1.setText("25.6°C");
                txtHumidity1.setText("69%");
                txtSoilMoisture1.setText("57%");
                txtLightLevel1.setText(
                        getString(R.string.good)
                );

                break;


            case "Bell Pepper":

                txtTemperature1.setText("26.4°C");
                txtHumidity1.setText("71%");
                txtSoilMoisture1.setText("60%");
                txtLightLevel1.setText(
                        getString(R.string.good)
                );

                break;


            case "Potato":

                txtTemperature1.setText("21.8°C");
                txtHumidity1.setText("76%");
                txtSoilMoisture1.setText("68%");
                txtLightLevel1.setText(
                        getString(R.string.good)
                );

                break;


            case "Raspberry":

                txtTemperature1.setText("22.6°C");
                txtHumidity1.setText("73%");
                txtSoilMoisture1.setText("64%");
                txtLightLevel1.setText(
                        getString(R.string.good)
                );

                break;


            case "Soybean":

                txtTemperature1.setText("27.1°C");
                txtHumidity1.setText("67%");
                txtSoilMoisture1.setText("59%");
                txtLightLevel1.setText(
                        getString(R.string.good)
                );

                break;


            case "Squash":

                txtTemperature1.setText("25.9°C");
                txtHumidity1.setText("75%");
                txtSoilMoisture1.setText("62%");
                txtLightLevel1.setText(
                        getString(R.string.good)
                );

                break;


            case "Strawberry":

                txtTemperature1.setText("20.7°C");
                txtHumidity1.setText("80%");
                txtSoilMoisture1.setText("70%");
                txtLightLevel1.setText(
                        getString(R.string.good)
                );

                break;


            case "Tomato":

                txtTemperature1.setText("27.8°C");
                txtHumidity1.setText("68%");
                txtSoilMoisture1.setText("60%");
                txtLightLevel1.setText(
                        getString(R.string.good)
                );

                break;


            default:

                txtTemperature1.setText("28.0°C");
                txtHumidity1.setText("70%");
                txtSoilMoisture1.setText("60%");
                txtLightLevel1.setText(
                        getString(R.string.good)
                );

                break;
        }


        txtPumpStatus1.setText(
                getString(R.string.off)
        );


        // =====================================================
        // CHECK SOIL MOISTURE THRESHOLD
        // =====================================================

        checkPlant1MoistureNotification();
    }


    // =========================================================
    // UPDATE PLANT 2 ENVIRONMENT
    // =========================================================

    private void updatePlant2Environment() {

        updatePlant2Image();


        switch (selectedPlant2) {

            case "Apple":

                txtTemperature2.setText("28.4°C");
                txtHumidity2.setText("72%");
                txtSoilMoisture2.setText("61%");
                txtLightLevel2.setText(
                        getString(R.string.good)
                );

                break;


            case "Blueberry":

                txtTemperature2.setText("24.5°C");
                txtHumidity2.setText("78%");
                txtSoilMoisture2.setText("66%");
                txtLightLevel2.setText(
                        getString(R.string.good)
                );

                break;


            case "Cherry":

                txtTemperature2.setText("23.8°C");
                txtHumidity2.setText("70%");
                txtSoilMoisture2.setText("58%");
                txtLightLevel2.setText(
                        getString(R.string.good)
                );

                break;


            case "Corn":

                txtTemperature2.setText("29.2°C");
                txtHumidity2.setText("65%");
                txtSoilMoisture2.setText("55%");
                txtLightLevel2.setText(
                        getString(R.string.good)
                );

                break;


            case "Grape":

                txtTemperature2.setText("26.7°C");
                txtHumidity2.setText("68%");
                txtSoilMoisture2.setText("52%");
                txtLightLevel2.setText(
                        getString(R.string.good)
                );

                break;


            case "Orange":

                txtTemperature2.setText("27.5°C");
                txtHumidity2.setText("74%");
                txtSoilMoisture2.setText("63%");
                txtLightLevel2.setText(
                        getString(R.string.good)
                );

                break;


            case "Peach":

                txtTemperature2.setText("25.6°C");
                txtHumidity2.setText("69%");
                txtSoilMoisture2.setText("57%");
                txtLightLevel2.setText(
                        getString(R.string.good)
                );

                break;


            case "Bell Pepper":

                txtTemperature2.setText("26.4°C");
                txtHumidity2.setText("71%");
                txtSoilMoisture2.setText("60%");
                txtLightLevel2.setText(
                        getString(R.string.good)
                );

                break;


            case "Potato":

                txtTemperature2.setText("21.8°C");
                txtHumidity2.setText("76%");
                txtSoilMoisture2.setText("68%");
                txtLightLevel2.setText(
                        getString(R.string.good)
                );

                break;


            case "Raspberry":

                txtTemperature2.setText("22.6°C");
                txtHumidity2.setText("73%");
                txtSoilMoisture2.setText("64%");
                txtLightLevel2.setText(
                        getString(R.string.good)
                );

                break;


            case "Soybean":

                txtTemperature2.setText("27.1°C");
                txtHumidity2.setText("67%");
                txtSoilMoisture2.setText("59%");
                txtLightLevel2.setText(
                        getString(R.string.good)
                );

                break;


            case "Squash":

                txtTemperature2.setText("25.9°C");
                txtHumidity2.setText("75%");
                txtSoilMoisture2.setText("62%");
                txtLightLevel2.setText(
                        getString(R.string.good)
                );

                break;


            case "Strawberry":

                txtTemperature2.setText("20.7°C");
                txtHumidity2.setText("80%");
                txtSoilMoisture2.setText("70%");
                txtLightLevel2.setText(
                        getString(R.string.good)
                );

                break;


            case "Tomato":

                // =================================================
                // TOMATO CURRENT SOIL MOISTURE
                // =================================================

                txtTemperature2.setText("27.8°C");
                txtHumidity2.setText("68%");
                txtSoilMoisture2.setText("43%");
                txtLightLevel2.setText(
                        getString(R.string.low)
                );

                break;


            default:

                txtTemperature2.setText("28.0°C");
                txtHumidity2.setText("70%");
                txtSoilMoisture2.setText("60%");
                txtLightLevel2.setText(
                        getString(R.string.good)
                );

                break;
        }


        txtPumpStatus2.setText(
                getString(R.string.off)
        );


        // =====================================================
        // CHECK SOIL MOISTURE THRESHOLD
        // =====================================================

        checkPlant2MoistureNotification();
    }
}