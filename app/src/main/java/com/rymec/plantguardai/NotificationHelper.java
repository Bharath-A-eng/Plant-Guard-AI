package com.rymec.plantguardai;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {

    // =========================================================
    // NOTIFICATION CHANNEL
    // =========================================================

    private static final String CHANNEL_ID =
            "plantguard_notifications";


    // =========================================================
    // CREATE NOTIFICATION CHANNEL
    // =========================================================

    public static void createNotificationChannel(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // -------------------------------------------------
            // Get localized channel name
            // -------------------------------------------------

            String channelName =
                    context.getString(
                            R.string.notification_channel_name
                    );


            // -------------------------------------------------
            // Get localized channel description
            // -------------------------------------------------

            String channelDescription =
                    context.getString(
                            R.string.notification_channel_description
                    );


            // -------------------------------------------------
            // Create channel
            // -------------------------------------------------

            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            channelName,
                            NotificationManager.IMPORTANCE_HIGH
                    );


            channel.setDescription(
                    channelDescription
            );


            // -------------------------------------------------
            // Enable vibration
            // -------------------------------------------------

            channel.enableVibration(true);


            // -------------------------------------------------
            // Notification manager
            // -------------------------------------------------

            NotificationManager notificationManager =
                    context.getSystemService(
                            NotificationManager.class
                    );


            if (notificationManager != null) {

                notificationManager.createNotificationChannel(
                        channel
                );
            }
        }
    }


    // =========================================================
    // SHOW THIRSTY NOTIFICATION
    //
    // 3-ARGUMENT VERSION
    // =========================================================

    public static void showThirstyNotification(
            Context context,
            String plantName,
            String moisture
    ) {

        // -----------------------------------------------------
        // Make sure notification channel exists
        // -----------------------------------------------------

        createNotificationChannel(context);


        // -----------------------------------------------------
        // Android 13+ notification permission
        // -----------------------------------------------------

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (context.checkSelfPermission(
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
        }


        // =====================================================
        // SAFETY CHECKS
        // =====================================================

        if (plantName == null ||
                plantName.trim().isEmpty()) {

            plantName =
                    context.getString(
                            R.string.notification_default_plant
                    );
        }


        if (moisture == null ||
                moisture.trim().isEmpty()) {

            moisture =
                    context.getString(
                            R.string.notification_default_moisture
                    );
        }


        // =====================================================
        // TRANSLATE PLANT NAME
        //
        // The incoming plant name may be:
        //
        // Tomato
        // Maize
        // Apple
        // etc.
        //
        // We convert it to the current app language.
        // =====================================================

        String localizedPlantName =
                getLocalizedPlantName(
                        context,
                        plantName
                );


        // =====================================================
        // LOCALIZED NOTIFICATION TITLE
        // =====================================================

        String title =
                context.getString(
                        R.string.notification_plant_thirsty_title,
                        localizedPlantName
                );


        // =====================================================
        // LOCALIZED NOTIFICATION MESSAGE
        // =====================================================

        String message =
                context.getString(
                        R.string.notification_plant_thirsty_message,
                        moisture
                );


        // =====================================================
        // CREATE NOTIFICATION
        // =====================================================

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(
                        context,
                        CHANNEL_ID
                )
                        .setSmallIcon(
                                R.drawable.ic_launcher_foreground
                        )
                        .setContentTitle(title)
                        .setContentText(message)
                        .setStyle(
                                new NotificationCompat.BigTextStyle()
                                        .bigText(message)
                        )
                        .setPriority(
                                NotificationCompat.PRIORITY_HIGH
                        )
                        .setCategory(
                                NotificationCompat.CATEGORY_REMINDER
                        )
                        .setAutoCancel(true)
                        .setVibrate(
                                new long[]{
                                        0,
                                        500,
                                        250,
                                        500
                                }
                        );


        // =====================================================
        // SHOW NOTIFICATION
        // =====================================================

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);


        try {

            notificationManager.notify(
                    getNotificationId(plantName),
                    builder.build()
            );

        } catch (SecurityException e) {

            // -------------------------------------------------
            // Notification permission was not granted.
            // Prevent application crash.
            // -------------------------------------------------

        }
    }


    // =========================================================
    // 2-ARGUMENT VERSION
    //
    // Allows:
    //
    // showThirstyNotification(context, plantName);
    //
    // =========================================================

    public static void showThirstyNotification(
            Context context,
            String plantName
    ) {

        String defaultMoisture =
                context.getString(
                        R.string.notification_default_moisture
                );


        showThirstyNotification(
                context,
                plantName,
                defaultMoisture
        );
    }


    // =========================================================
    // LOCALIZED PLANT NAME
    //
    // Converts the plant name received from the application
    // into the language currently selected by Android.
    //
    // IMPORTANT:
    // The names below must exist in every strings.xml file.
    // =========================================================

    private static String getLocalizedPlantName(
            Context context,
            String plantName
    ) {

        if (plantName == null) {

            return context.getString(
                    R.string.notification_default_plant
            );
        }


        String normalizedName =
                plantName
                        .trim()
                        .toLowerCase();


        // =====================================================
        // TOMATO
        // =====================================================

        if (normalizedName.equals("tomato")) {

            return context.getString(
                    R.string.tomato
            );
        }


        // =====================================================
        // MAIZE
        // =====================================================

        if (normalizedName.equals("maize")) {

            return context.getString(
                    R.string.maize
            );
        }


        // =====================================================
        // CORN
        // =====================================================

        if (normalizedName.equals("corn")) {

            return context.getString(
                    R.string.corn
            );
        }


        // =====================================================
        // APPLE
        // =====================================================

        if (normalizedName.equals("apple")) {

            return context.getString(
                    R.string.apple
            );
        }


        // =====================================================
        // BLUEBERRY
        // =====================================================

        if (normalizedName.equals("blueberry")) {

            return context.getString(
                    R.string.blueberry
            );
        }


        // =====================================================
        // CHERRY
        // =====================================================

        if (normalizedName.equals("cherry")) {

            return context.getString(
                    R.string.cherry
            );
        }


        // =====================================================
        // GRAPE
        // =====================================================

        if (normalizedName.equals("grape")) {

            return context.getString(
                    R.string.grape
            );
        }


        // =====================================================
        // ORANGE
        // =====================================================

        if (normalizedName.equals("orange")) {

            return context.getString(
                    R.string.orange
            );
        }


        // =====================================================
        // PEACH
        // =====================================================

        if (normalizedName.equals("peach")) {

            return context.getString(
                    R.string.peach
            );
        }


        // =====================================================
        // BELL PEPPER
        // =====================================================

        if (normalizedName.equals("bell pepper") ||
                normalizedName.equals("bell_pepper") ||
                normalizedName.equals("pepper")) {

            return context.getString(
                    R.string.bell_pepper
            );
        }


        // =====================================================
        // POTATO
        // =====================================================

        if (normalizedName.equals("potato")) {

            return context.getString(
                    R.string.potato
            );
        }


        // =====================================================
        // RASPBERRY
        // =====================================================

        if (normalizedName.equals("raspberry")) {

            return context.getString(
                    R.string.raspberry
            );
        }


        // =====================================================
        // SOYBEAN
        // =====================================================

        if (normalizedName.equals("soybean")) {

            return context.getString(
                    R.string.soybean
            );
        }


        // =====================================================
        // SQUASH
        // =====================================================

        if (normalizedName.equals("squash")) {

            return context.getString(
                    R.string.squash
            );
        }


        // =====================================================
        // STRAWBERRY
        // =====================================================

        if (normalizedName.equals("strawberry")) {

            return context.getString(
                    R.string.strawberry
            );
        }


        // =====================================================
        // FALLBACK
        // =====================================================

        return plantName;
    }


    // =========================================================
    // UNIQUE NOTIFICATION ID
    // =========================================================

    private static int getNotificationId(
            String plantName
    ) {

        return Math.abs(
                plantName.hashCode()
        );
    }
}