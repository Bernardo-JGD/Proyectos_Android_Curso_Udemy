package com.example.seccion_6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.seccion_6.EjercicioSeccion.Activity.FrutaVerduraActivity;
import com.example.seccion_6.EjercicioSeccion.Modelo.FrutaVerdura;
import com.example.seccion_6.StarCraftUnidades_RecyclerCard.ComandantesStarCraftDosActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnIrRecyclerView;
    private Button btnIrCardView;
    private Button btnIrComandantes;
    private Button btnIrFrutasVerduras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIrRecyclerView = (Button) findViewById(R.id.btnIrRecyclerView);
        btnIrCardView = (Button) findViewById(R.id.btnIrCardView);
        btnIrComandantes = (Button) findViewById(R.id.btnIrComandantes);
        btnIrFrutasVerduras = (Button) findViewById(R.id.btnIrFrutasVerduras);

        btnIrRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
            }
        });

        btnIrCardView.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               Intent intent = new Intent(MainActivity.this, CardViewActivity.class);
               startActivity(intent);
           }
        });

        btnIrComandantes.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               Intent intent = new Intent(MainActivity.this, ComandantesStarCraftDosActivity.class);
               startActivity(intent);
           }
        });

        btnIrFrutasVerduras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FrutaVerduraActivity.class);
                startActivity(intent);
            }
        });

    }
}