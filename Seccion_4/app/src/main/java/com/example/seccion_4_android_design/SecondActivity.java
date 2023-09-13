package com.example.seccion_4_android_design;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private TextView tv1;
    private Button btnActivity_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Boton superior izquierdo para regresar
        //Al parecer funcionar sin modificar el manifest con la etiqueta meta-data
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_protos);

        tv1 = (TextView) findViewById(R.id.tv1);
        btnActivity_2 = (Button) findViewById(R.id.btnActivity_2);

        Bundle bundle = getIntent().getExtras();
        //Con la segunda condición me aseguro que exista una variable con la llave indicada
        if(bundle != null && bundle.getString("mensaje") != null){
            String mensajeRecibido = bundle.getString("mensaje");
            Toast.makeText(SecondActivity.this, "Intent recibido", Toast.LENGTH_LONG).show();
            tv1.setText(mensajeRecibido);
        }else{
            Toast.makeText(SecondActivity.this, "No se recibió el intent correctamente", Toast.LENGTH_LONG).show();
        }

        btnActivity_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });

    }
}