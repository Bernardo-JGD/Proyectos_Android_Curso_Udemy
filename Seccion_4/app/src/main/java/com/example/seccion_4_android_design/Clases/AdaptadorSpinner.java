package com.example.seccion_4_android_design.Clases;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class AdaptadorSpinner extends ArrayAdapter<String> {


    public AdaptadorSpinner(@NonNull Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }
}
