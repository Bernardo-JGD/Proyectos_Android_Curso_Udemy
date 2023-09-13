package com.example.seccion_4_android_design;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnActivity_1;
    private Button btnActivityEjercicio;
    private final String mensaje = "Yendo al activity 2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Estas dos lineas sirven para mostrar el icono en el action_bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //El icono lo puedo crear: File>New>ImageAsset
        getSupportActionBar().setIcon(R.mipmap.ic_protos);

        btnActivity_1 = (Button) findViewById(R.id.btnActivity1);
        btnActivityEjercicio = (Button) findViewById(R.id.btnActivityEjercicio);

        btnActivity_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Codigo para ir a un activity y mandar un parámetro (se pueden mandar varios)
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("mensaje", mensaje);
                startActivity(intent);
            }
        });

        btnActivityEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Ejercicio_parte_1.class);
                startActivity(intent);
            }
        });
    }
}