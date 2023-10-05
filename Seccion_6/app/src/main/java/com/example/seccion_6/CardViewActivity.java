package com.example.seccion_6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CardViewActivity extends AppCompatActivity {

    private TextView tvPrueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);

        getSupportActionBar().setTitle("CardView");

        tvPrueba = (TextView) findViewById(R.id.tvPrueba);
        tvPrueba.setText("Probando CardView");

    }
}