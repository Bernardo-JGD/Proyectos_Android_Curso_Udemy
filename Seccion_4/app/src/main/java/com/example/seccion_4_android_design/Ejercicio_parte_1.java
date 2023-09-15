package com.example.seccion_4_android_design;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    private int total = 0;
    private int kilos = 1;
    private boolean haSeleccionadoFruta = false;
    private String formaPagoSeleccionada = "";
    private int saldoTarjeta = 0;

    private SeekBar seekBarKilosFruta;
    private RadioGroup rgFrutas;
    private Spinner spinnerMetodoPago;
    private Button btnPagar;
    private EditText edEfectivo;
    private EditText edIngresarSaldo;
    private Button btnIngresarSaldo;

    private TextView tvTotalFruta;
    private TextView tvNumeroKg;
    private TextView tvSaldoTarjeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio_parte1);

        rgFrutas = (RadioGroup) findViewById(R.id.rgFrutas);
        seekBarKilosFruta = (SeekBar) findViewById(R.id.seekBarKilosFruta);
        tvTotalFruta = (TextView) findViewById(R.id.tvTotalFruta);
        tvNumeroKg = (TextView) findViewById(R.id.tvNumeroKg);
        spinnerMetodoPago = (Spinner) findViewById(R.id.spinnerMetodoPago);
        edEfectivo = (EditText) findViewById(R.id.edEfectivo);
        edIngresarSaldo = (EditText) findViewById(R.id.edIngresarSaldo);
        tvSaldoTarjeta = (TextView) findViewById(R.id.tvSaldoTarjeta);
        btnIngresarSaldo = (Button) findViewById(R.id.btnIngresarSaldo);
        btnPagar = (Button) findViewById(R.id.btnPagar);

        String[] metodosPago = {"Efectivo", "Tarjeta"};
        ArrayAdapter<String> adaptadorSpinner = new ArrayAdapter<String>(this, R.layout.spinner_item_forma_pago, metodosPago);
        spinnerMetodoPago.setAdapter(adaptadorSpinner);

        rgFrutas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                kilos = 1;
                String totalString = "Total: $";
                seekBarKilosFruta.setProgress(kilos);
                frutaSeleccionada = checkedId;
                switch(checkedId){

                    case rbIdMango:
                        seekBarKilosFruta.setMax(maxMango);
                        total = calcularPrecio(precioMango);
                        haSeleccionadoFruta = true;
                        break;

                    case rbIdPlatano:
                        seekBarKilosFruta.setMax(maxPlatano);
                        total = calcularPrecio(precioPlatano);
                        haSeleccionadoFruta = true;
                        break;

                    case rbIdMelon:
                        seekBarKilosFruta.setMax(maxMelon);
                        total = calcularPrecio(precioMelon);
                        haSeleccionadoFruta = true;
                        break;

                    default:
                        total = calcularPrecio(1);
                        break;

                }
                tvTotalFruta.setText("Total $ " + total);
                tvNumeroKg.setText("Numero Kg: " + kilos);
            }

        });

        seekBarKilosFruta.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                kilos = progress;
                tvNumeroKg.setText("Numero Kg: " + kilos);
                total = calcularPrecio(1);
                tvTotalFruta.setText("Total: $ " + total);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        spinnerMetodoPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                formaPagoSeleccionada = parent.getItemAtPosition(position).toString();
                if(formaPagoSeleccionada.equals("Efectivo")){
                    habilitarPagoTarjeta(View.GONE);//Invisible
                    edEfectivo.setVisibility(View.VISIBLE);//Visible
                }else if(formaPagoSeleccionada.equals("Tarjeta")){
                    habilitarPagoTarjeta(View.VISIBLE);//Visible
                    edEfectivo.setVisibility(View.GONE);//Invisible
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnIngresarSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String saldoIngresado = edIngresarSaldo.getText().toString();
                if(!saldoIngresado.equals("") && !saldoIngresado.isEmpty()){
                    saldoTarjeta += Integer.parseInt(saldoIngresado);
                    tvSaldoTarjeta.setText("Saldo: $ " + saldoTarjeta);
                    edIngresarSaldo.setText("");
                }else{
                    Toast.makeText(Ejercicio_parte_1.this, "Ingrese una cantidad por favor", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(haSeleccionadoFruta){
                    int cambio;
                    if(formaPagoSeleccionada.equals("Efectivo")){
                        int efectivo = Integer.parseInt(edEfectivo.getText().toString());

                        if(efectivo>=total){
                            cambio = efectivo-total;
                            Toast.makeText(Ejercicio_parte_1.this, "Su cambio es de: $" + (cambio), Toast.LENGTH_LONG).show();
                            Toast.makeText(Ejercicio_parte_1.this, "Gracias por su compra", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Ejercicio_parte_1.this, "Pago insuficiente", Toast.LENGTH_LONG).show();
                        }

                    }else if(formaPagoSeleccionada.equals("Tarjeta")){
                        if(saldoTarjeta>=total){
                            cambio = saldoTarjeta - total;
                            Toast.makeText(Ejercicio_parte_1.this, "Su cambio es de: $" + (cambio), Toast.LENGTH_LONG).show();
                            Toast.makeText(Ejercicio_parte_1.this, "Gracias por su compra", Toast.LENGTH_LONG).show();
                            saldoTarjeta = cambio;
                            tvSaldoTarjeta.setText("Saldo: $ " + saldoTarjeta);
                        }else{
                            Toast.makeText(Ejercicio_parte_1.this, "Pago insuficiente", Toast.LENGTH_LONG).show();
                        }
                    }

                }else{
                    Toast.makeText(Ejercicio_parte_1.this, "Seleccione una fruta por favor", Toast.LENGTH_LONG).show();
                }

            }




        });
    }

    private int calcularPrecio(int precioFruta){

        if(frutaSeleccionada == rbIdMango){
            return (precioMango * kilos);
        }else if(frutaSeleccionada == rbIdPlatano){
            return (precioPlatano * kilos);
        }else if(frutaSeleccionada == rbIdMelon){
            return (precioMelon * kilos);
        }

        return (precioFruta*kilos);
    }

    private void habilitarPagoTarjeta(int visibilidad){
        tvSaldoTarjeta.setVisibility(visibilidad);
        edIngresarSaldo.setVisibility(visibilidad);
        btnIngresarSaldo.setVisibility(visibilidad);
    }

}












