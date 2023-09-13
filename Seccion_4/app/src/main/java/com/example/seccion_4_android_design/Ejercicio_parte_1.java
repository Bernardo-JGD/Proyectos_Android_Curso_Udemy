package com.example.seccion_4_android_design;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Ejercicio_parte_1 extends AppCompatActivity {

    /*
    * En este activity deberá escoger una fruta y la cantidad de kg que llevará
    * En el segundo deberá indicar el método de pago
    * Si es con tarjeta indicar los datos y revisar si el saldo es el suficiente
    * Si es en efectivo indicar la cantidad
    * Se encontrará un botón para confirmar pago
    * Si el pago es exacto mostrar compra exitosa
    * Si el pago es igual o más mostrar compra exitosa y el cambio
    * Si el pago no es suficiente mostrar saldo insuficiente o insuficiente segun el caso
    * */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio_parte1);
    }
}