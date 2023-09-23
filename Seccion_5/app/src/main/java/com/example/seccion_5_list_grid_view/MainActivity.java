package com.example.seccion_5_list_grid_view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.seccion_5_list_grid_view.ProyectoFrutas.Activitys.MainRegistrarFrutas;

public class MainActivity extends AppCompatActivity {

    private Button btnIrSimpleListView;
    private Button btnIrGridView;
    private Button btnIrPrueba;
    private Button btnIrFrutas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIrSimpleListView = (Button) findViewById(R.id.btnIrSimpleListView);
        btnIrGridView = (Button) findViewById(R.id.btnIrGridView);
        btnIrPrueba = (Button) findViewById(R.id.btnIrPrueba);
        btnIrFrutas = (Button) findViewById(R.id.btnIrFrutas);

        btnIrSimpleListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SimpleListView.class);
                startActivity(intent);
            }
        });

        btnIrGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GridViewActivity.class);
                startActivity(intent);
            }
        });

        btnIrPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListViewPruebaActivity.class);
                startActivity(intent);
            }
        });

        btnIrFrutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainRegistrarFrutas.class);
                startActivity(intent);
            }
        });

    }
}