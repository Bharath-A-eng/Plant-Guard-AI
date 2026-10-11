package com.rymec.plantguardai;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SupportedPlantsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<PlantModel> plantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LocaleHelper.loadLocale(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supported_plants);

        recyclerView = findViewById(R.id.recyclerPlants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        plantList = new ArrayList<>();

        plantList.add(new PlantModel(
                "Apple",
                getString(R.string.apple),
                getString(R.string.apple_diseases),
                R.drawable.apple));

        plantList.add(new PlantModel(
                "Blueberry",
                getString(R.string.blueberry),
                getString(R.string.blueberry_diseases),
                R.drawable.blueberry));

        plantList.add(new PlantModel(
                "Cherry",
                getString(R.string.cherry),
                getString(R.string.cherry_diseases),
                R.drawable.cherry));

       plantList.add(new PlantModel(
                "Corn",
                getString(R.string.corn),
                getString(R.string.corn_diseases),
                R.drawable.corn));

        plantList.add(new PlantModel(
                "Grape",
                getString(R.string.grape),
                getString(R.string.grape_diseases),
                R.drawable.grape));

        plantList.add(new PlantModel(
                "Orange",
                getString(R.string.orange),
                getString(R.string.orange_diseases),
                R.drawable.orange));

        plantList.add(new PlantModel(
                "Peach",
                getString(R.string.peach),
                getString(R.string.peach_diseases),
                R.drawable.peach));

        plantList.add(new PlantModel(
                "Bell Pepper",
                getString(R.string.bell_pepper),
                getString(R.string.pepper_diseases),
                R.drawable.pepper));

        plantList.add(new PlantModel(
                "Potato",
                getString(R.string.potato),
                getString(R.string.potato_diseases),
                R.drawable.potato));

         plantList.add( new PlantModel(
                "Raspberry",
                getString(R.string.raspberry),
                getString(R.string.raspberry_diseases),
                R.drawable.raspberry));

        plantList.add(new PlantModel(
                "Soybean",
                getString(R.string.soybean),
                getString(R.string.soybean_diseases),
                R.drawable.soybean));

        plantList.add(new PlantModel(
                "Squash",
                getString(R.string.squash),
                getString(R.string.squash_diseases),
                R.drawable.squash));

        plantList.add(new PlantModel(
                "Strawberry",
                getString(R.string.strawberry),
                getString(R.string.strawberry_diseases),
                R.drawable.strawberry));

        plantList.add(new PlantModel(
                "Tomato",
                getString(R.string.tomato),
                getString(R.string.tomato_diseases),
                R.drawable.tomato));

        recyclerView.setAdapter(new PlantAdapter(plantList));
    }
}