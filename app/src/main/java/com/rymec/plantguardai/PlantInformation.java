package com.rymec.plantguardai;


import android.content.Context;
public class PlantInformation {

    // ================= CARE TIPS =================

    public static String getCareTips(Context context, String plantName) {

        switch (plantName) {

            case "Apple":
                return context.getString(R.string.apple_care);

            case "Blueberry":
                return context.getString(R.string.blueberry_care);

            case "Cherry":
                return context.getString(R.string.cherry_care);

            case "Corn":
                return context.getString(R.string.corn_care);

            case "Grape":
                return context.getString(R.string.grape_care);

            case "Orange":
                return context.getString(R.string.orange_care);

            case "Tomato":
                return context.getString(R.string.tomato_care);

            case "Bell Pepper":
                return context.getString(R.string.bell_pepper_care);

            case "Peach":
                return context.getString(R.string.peach_care);

            case "Potato":
                return context.getString(R.string.potato_care);

            case "Raspberry":
                return context.getString(R.string.raspberry_care);

            case "Soybean":
                return context.getString(R.string.soybean_care);

            case "Squash":
                return context.getString(R.string.squash_care);

            case "Strawberry":
                return context.getString(R.string.strawberry_care);

            default:
                return context.getString(R.string.sample_care);
        }
    }

    // ================= PREVENTION =================

    public static String getPrevention(Context context, String plantName) {

        switch (plantName) {

            case "Apple":
                return context.getString(R.string.apple_prevention);

            case "Blueberry":
                return context.getString(R.string.blueberry_prevention);

            case "Cherry":
                return context.getString(R.string.cherry_prevention);

            case "Corn":
                return context.getString(R.string.corn_prevention);

            case "Grape":
                return context.getString(R.string.grape_prevention);

            case "Orange":
                return context.getString(R.string.orange_prevention);

            case "Tomato":
                return context.getString(R.string.tomato_prevention);

            case "Bell Pepper":
                return context.getString(R.string.bell_pepper_prevention);

            case "Peach":
                return context.getString(R.string.peach_prevention);

            case "Potato":
                return context.getString(R.string.potato_prevention);

            case "Raspberry":
                return context.getString(R.string.raspberry_prevention);

            case "Soybean":
                return context.getString(R.string.soybean_prevention);

            case "Squash":
                return context.getString(R.string.squash_prevention);

            case "Strawberry":
                return context.getString(R.string.strawberry_prevention);

            default:
                return context.getString(R.string.sample_prevention);
        }
    }

    // ================= TREATMENT =================

    public static String getTreatment(Context context, String plantName) {

        switch (plantName) {

            case "Apple":
                return context.getString(R.string.apple_treatment);

            case "Blueberry":
                return context.getString(R.string.blueberry_treatment);

            case "Cherry":
                return context.getString(R.string.cherry_treatment);


            case "Corn":
                return context.getString(R.string.corn_treatment);


            case "Grape":
                return context.getString(R.string.grape_treatment);


            case "Orange":
                return context.getString(R.string.orange_treatment);


            case "Tomato":
                return context.getString(R.string.tomato_treatment);


            case "Bell Pepper":
                return context.getString(R.string.bell_pepper_treatment);


            case "Peach":
                return context.getString(R.string.peach_treatment);

            case "Potato":
                return context.getString(R.string.potato_treatment);


            case "Raspberry":
                return context.getString(R.string.raspberry_treatment);


            case "Soybean":
                return context.getString(R.string.soybean_treatment);


            case "Squash":
                return context.getString(R.string.squash_treatment);


            case "Strawberry":
                return context.getString(R.string.strawberry_treatment);


            default:
                return context.getString(R.string.sample_treatment);
        }
    }
}