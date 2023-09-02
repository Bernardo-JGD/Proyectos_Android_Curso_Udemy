package com.example.seccion_4_android_design;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnActivity_1;
    private final String mensaje = "Yendo al activity 2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActivity_1 = (Button) findViewById(R.id.btnActivity1);

        btnActivity_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Codigo para ir a un activity y mandar un parámetro (se pueden mandar varios)
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("mensaje", mensaje);
                startActivity(intent);
            }
        });
    }
}