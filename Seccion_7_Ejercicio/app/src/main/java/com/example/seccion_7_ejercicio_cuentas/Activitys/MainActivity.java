package com.example.seccion_7_ejercicio_cuentas.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.example.seccion_7_ejercicio_cuentas.Adapters.AdapterSpinnerTipoMovimiento;
import com.example.seccion_7_ejercicio_cuentas.R;

public class MainActivity extends AppCompatActivity {

    //Elementos Spinner
    private Spinner spinnerTipoMovimiento;
    private String[] listaMovimientos;
    private AdapterSpinnerTipoMovimiento adapterSpinnerTipoMovimiento;
    private String movimientoSeleccionado;

    //Elementos cliente seleccionado en Spinner
    private AutoCompleteTextView autoCompleteTextViewBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerTipoMovimiento = (Spinner) findViewById(R.id.spinnerTipoMovimiento);
        autoCompleteTextViewBuscar = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewBuscar);

        listaMovimientos = getResources().getStringArray(R.array.spinnerOpcionesMovimientos);
        adapterSpinnerTipoMovimiento = new AdapterSpinnerTipoMovimiento(this, R.layout.item_spinner_tipo_movimiento, listaMovimientos);
        spinnerTipoMovimiento.setAdapter(adapterSpinnerTipoMovimiento);

        spinnerTipoMovimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                movimientoSeleccionado = parent.getItemAtPosition(position).toString();
                if(movimientoSeleccionado.equals("Todos")){
                    desHabilitarClientes();
                }else if(movimientoSeleccionado.equals("Abonos")){
                    desHabilitarClientes();
                }else if(movimientoSeleccionado.equals("Gastos")){
                    desHabilitarClientes();
                }else if(movimientoSeleccionado.equals("Clientes")){
                    habilitarClientes();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void habilitarClientes(){
        autoCompleteTextViewBuscar.setVisibility(View.VISIBLE);
    }

    private void desHabilitarClientes(){
        autoCompleteTextViewBuscar.setVisibility(View.GONE);
    }
}