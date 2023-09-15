package com.example.seccion_4_android_design;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

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

    private final int rbIdMango = R.id.rbMango;
    private final int rbIdPlatano = R.id.rbPlatano;
    private final int rbIdMelon = R.id.rbMelon;

    private int frutaSeleccionada;

    private static final int precioMango = 55;
    private static final int precioPlatano = 35;
    private static final int precioMelon = 45;

    private static final int maxMango = 4;
    private static final int maxPlatano = 6;
    private static final int maxMelon = 8;

    private SeekBar seekBarKilosFruta;
    private RadioGroup rgFrutas;
    private Spinner spinnerMetodoPago;

    private TextView tvTotalFruta;
    private TextView tvNumeroKg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio_parte1);

        rgFrutas = (RadioGroup) findViewById(R.id.rgFrutas);
        seekBarKilosFruta = (SeekBar) findViewById(R.id.seekBarKilosFruta);
        tvTotalFruta = (TextView) findViewById(R.id.tvTotalFruta);
        tvNumeroKg = (TextView) findViewById(R.id.tvNumeroKg);
        spinnerMetodoPago = (Spinner) findViewById(R.id.spinnerMetodoPago);

        String[] metodosPago = {"Efectivo", "Tarjeta"};
        ArrayAdapter<String> adaptadorSpinner = new ArrayAdapter<String>(this, R.layout.spinner_item_forma_pago, metodosPago);
        spinnerMetodoPago.setAdapter(adaptadorSpinner);

        rgFrutas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int kilos = 1;
                int total = 0;
                String totalString = "Total: $";
                seekBarKilosFruta.setProgress(kilos);
                frutaSeleccionada = checkedId;
                switch(checkedId){

                    case rbIdMango:
                        seekBarKilosFruta.setMax(maxMango);
                        total = calcularPrecio(precioMango, kilos);
                        break;

                    case rbIdPlatano:
                        seekBarKilosFruta.setMax(maxPlatano);
                        total = calcularPrecio(precioPlatano, kilos);
                        break;

                    case rbIdMelon:
                        seekBarKilosFruta.setMax(maxMelon);
                        total = calcularPrecio(precioMelon, kilos);
                        break;

                    default:
                        total = calcularPrecio(1, kilos);
                        break;

                }
                tvTotalFruta.setText("Total $ " + total);
                tvNumeroKg.setText("Numero Kg: " + kilos);
            }

        });

        seekBarKilosFruta.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvNumeroKg.setText("Numero Kg: " + progress);
                int total = calcularPrecio(1, progress);
                tvTotalFruta.setText("Total: $ " + total);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private int calcularPrecio(int precioFruta, int kilos){

        if(frutaSeleccionada == rbIdMango){
            return (precioMango * kilos);
        }else if(frutaSeleccionada == rbIdPlatano){
            return (precioPlatano * kilos);
        }else if(frutaSeleccionada == rbIdMelon){
            return (precioMelon * kilos);
        }

        return (precioFruta*kilos);
    }
}
