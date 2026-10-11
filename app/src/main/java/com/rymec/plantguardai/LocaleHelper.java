package com.rymec.plantguardai;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

public class LocaleHelper {

    private static final String PREF_NAME = "Settings";
    private static final String KEY_LANGUAGE = "language";

    public static void setLocale(Context context, String language) {

        SharedPreferences.Editor editor =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();

        editor.putString(KEY_LANGUAGE, language);
        editor.apply();

        AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(language)
        );
    }

    public static void loadLocale(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        String language = prefs.getString(KEY_LANGUAGE, "en");

        AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(language)
        );
    }
}