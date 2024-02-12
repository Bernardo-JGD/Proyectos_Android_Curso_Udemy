package com.jbgd.seccion_8_shared_preferences_splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import com.jbgd.seccion_8_shared_preferences_splashscreen.Utils.Util;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        Intent intentLogin = new Intent(SplashActivity.this, LoginActivity.class);
        Intent intentMain = new Intent(SplashActivity.this, MainActivity.class);

        if(!TextUtils.isEmpty(Util.getUserMailPreferences(preferences)) && !TextUtils.isEmpty(Util.getUserPasswordPreferences(preferences))){
            startActivity(intentMain);
        }else{
            startActivity(intentLogin);
        }


    }
}