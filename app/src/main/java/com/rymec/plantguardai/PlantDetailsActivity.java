package com.rymec.plantguardai;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PlantDetailsActivity extends AppCompatActivity {

    ImageView imgPlant;
    TextView txtPlantName;
    TextView txtDiseases;
    TextView txtCare;
    TextView txtPrevention;
    TextView txtTreatment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LocaleHelper.loadLocale(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);

        // Initialize Views
        imgPlant = findViewById(R.id.imgPlant);
        txtPlantName = findViewById(R.id.txtPlantName);
        txtDiseases = findViewById(R.id.txtDiseases);
        txtCare = findViewById(R.id.txtCare);
        txtPrevention = findViewById(R.id.txtPrevention);
        txtTreatment = findViewById(R.id.txtTreatment);

        // Receive data from PlantAdapter
        String plantName = getIntent().getStringExtra("plantName");
        txtPlantName.setText(plantName + " | " + plantName.length());
        String diseases = getIntent().getStringExtra("plantDiseases");
        int image = getIntent().getIntExtra("plantImage", 0);

        // Display plant information
        txtPlantName.setText(plantName);
        txtDiseases.setText(diseases);
        imgPlant.setImageResource(image);

        // Load additional information
        txtCare.setText(PlantInformation.getCareTips(this, plantName));
        txtPrevention.setText(PlantInformation.getPrevention(this, plantName));
        txtTreatment.setText(PlantInformation.getTreatment(this, plantName));
    }
}