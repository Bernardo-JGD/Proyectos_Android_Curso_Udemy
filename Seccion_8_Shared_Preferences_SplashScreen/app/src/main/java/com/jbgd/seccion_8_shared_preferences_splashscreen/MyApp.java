package com.jbgd.seccion_8_shared_preferences_splashscreen;

import android.app.Application;
import android.os.SystemClock;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SystemClock.sleep(5000);
    }
}
