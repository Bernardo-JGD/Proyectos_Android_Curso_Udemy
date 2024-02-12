package com.jbgd.seccion_8_shared_preferences_splashscreen.Utils;

import android.content.SharedPreferences;

public class Util {

    public static String getUserMailPreferences(SharedPreferences preferences){
        return preferences.getString("email", "");
    }

    public static String getUserPasswordPreferences(SharedPreferences preferences){
        return preferences.getString("password", "");
    }

    public static void removeSharedPreferences(SharedPreferences preferences){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.apply();
    }

}
