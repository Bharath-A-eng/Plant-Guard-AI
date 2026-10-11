package com.rymec.plantguardai;

public class HistoryModel {

    private int id;
    private String image;
    private String plant;
    private String disease;
    private float confidence;
    private String date;
    private String time;

    public HistoryModel(int id,
                        String image,
                        String plant,
                        String disease,
                        float confidence,
                        String date,
                        String time) {

        this.id = id;
        this.image = image;
        this.plant = plant;
        this.disease = disease;
        this.confidence = confidence;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getPlant() {
        return plant;
    }

    public String getDisease() {
        return disease;
    }

    public float getConfidence() {
        return confidence;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}