package com.rymec.plantguardai;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.SharedPreferences;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatDelegate;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LocaleHelper.loadLocale(this);

        SharedPreferences preferences =
                getSharedPreferences("app_settings", MODE_PRIVATE);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btnLanguage = findViewById(R.id.btnLanguage);
        Button btnAbout = findViewById(R.id.btnAbout);
        Button btnPlants = findViewById(R.id.btnPlants);
        Button btnHistory = findViewById(R.id.btnHistory);
        Button btnShare = findViewById(R.id.btnShare);
        Button btnRate = findViewById(R.id.btnRate);
        Button btnPrivacy = findViewById(R.id.btnPrivacy);

        Switch switchDarkMode = findViewById(R.id.switchDarkMode);

        boolean isDarkMode =
                AppCompatDelegate.getDefaultNightMode()
                        == AppCompatDelegate.MODE_NIGHT_YES;

        switchDarkMode.setChecked(isDarkMode);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                );

            } else {

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                );

            }

        });

        // Language
        btnLanguage.setOnClickListener(v -> {

            String[] languages = {
                    "English",
                    "ಕನ್ನಡ",
                    "हिन्दी",
                    "తెలుగు",
                    "தமிழ்",
                    "മലയാളം"
            };

            String[] languageCodes = {
                    "en",
                    "kn",
                    "hi",
                    "te",
                    "ta",
                    "ml"
            };

            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.select_language))
                    .setItems(languages, (dialog, which) -> {

                        LocaleHelper.setLocale(SettingsActivity.this, languageCodes[which]);

                        Intent intent = getIntent();
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(intent);
                        overridePendingTransition(0, 0);

                    })
                    .show();
        });

        // About App
        btnAbout.setOnClickListener(v -> {

            new AlertDialog.Builder(this)
                    .setTitle(R.string.about_title)
                    .setMessage(getString(R.string.about_message))
                    .setPositiveButton(R.string.ok, null)
                    .show();

        });

        // Supported Plants
        btnPlants.setOnClickListener(v -> {

            Intent intent = new Intent(
                    SettingsActivity.this,
                    SupportedPlantsActivity.class
            );

            startActivity(intent);

        });

        // Scan History
        btnHistory.setOnClickListener(v ->

                startActivity(new Intent(
                        SettingsActivity.this,
                        HistoryActivity.class
                ))

        );

        // Share App
        btnShare.setOnClickListener(v -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    getString(R.string.share_message)
            );

            startActivity(Intent.createChooser(
                    shareIntent,
                    getString(R.string.share_app)
            ));

        });

        // Rate App
        btnRate.setOnClickListener(v ->

                Toast.makeText(
                        this,
                        getString(R.string.playstore_coming_soon),
                        Toast.LENGTH_SHORT
                ).show()

        );

        // Privacy Policy
        btnPrivacy.setOnClickListener(v -> {

            new AlertDialog.Builder(this)
                    .setTitle(R.string.privacy_policy)
                    .setMessage(getString(R.string.privacy_message))
                    .setPositiveButton(R.string.ok, null)
                    .show();

        });

    }
}