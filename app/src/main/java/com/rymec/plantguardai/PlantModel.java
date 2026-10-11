package com.rymec.plantguardai;

public class PlantModel {

    private String plantId;      // Internal ID (never translated)
    private String plantName;    // Display name (translated)
    private String diseases;
    private int image;

    public PlantModel(String plantId, String plantName, String diseases, int image) {
        this.plantId = plantId;
        this.plantName = plantName;
        this.diseases = diseases;
        this.image = image;
    }

    public String getPlantId() {
        return plantId;
    }

    public String getPlantName() {
        return plantName;
    }

    public String getDiseases() {
        return diseases;
    }

    public int getImage() {
        return image;
    }
}