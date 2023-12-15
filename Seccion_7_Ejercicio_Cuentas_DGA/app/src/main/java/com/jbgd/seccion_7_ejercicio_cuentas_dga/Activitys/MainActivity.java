package com.jbgd.seccion_7_ejercicio_cuentas_dga.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters.AdapterSpinnerTipoMovimiento;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.R;

public class MainActivity extends AppCompatActivity {

    //Elementos Spinner
    private Spinner spinnerTipoMovimiento;
    private String[] listaMovimientos;
    private AdapterSpinnerTipoMovimiento adapterSpinnerTipoMovimiento;
    private String movimientoSeleccionado;

    //Elementos cliente seleccionado en Spinner
    private AutoCompleteTextView autoCompleteTextViewBuscar;

    //RecyclersViews
    private RecyclerView recyclerViewTodosMovimientos;
    private RecyclerView recyclerViewAbonos;
    private RecyclerView recyclerViewGastos;
    private RecyclerView recyclerViewClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerTipoMovimiento = (Spinner) findViewById(R.id.spinnerTipoMovimiento);
        autoCompleteTextViewBuscar = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewBuscar);

        recyclerViewTodosMovimientos = (RecyclerView) findViewById(R.id.recyclerViewTodosMovimientos);
        recyclerViewAbonos = (RecyclerView) findViewById(R.id.recyclerViewAbonos);
        recyclerViewGastos = (RecyclerView) findViewById(R.id.recyclerViewGastos);
        recyclerViewClientes = (RecyclerView) findViewById(R.id.recyclerViewClientes);

        listaMovimientos = getResources().getStringArray(R.array.spinnerOpcionesMovimientos);
        adapterSpinnerTipoMovimiento = new AdapterSpinnerTipoMovimiento(this, R.layout.item_spinner_tipo_movimiento, listaMovimientos);
        spinnerTipoMovimiento.setAdapter(adapterSpinnerTipoMovimiento);

        spinnerTipoMovimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                movimientoSeleccionado = parent.getItemAtPosition(position).toString();
                if(movimientoSeleccionado.equals("Todos")){
                    habilitarTodosMovimientos();
                }else if(movimientoSeleccionado.equals("Abonos")){
                    habilitarAbonos();
                }else if(movimientoSeleccionado.equals("Gastos")){
                    habilitarGastos();
                }else if(movimientoSeleccionado.equals("Clientes")){
                    habilitarClientes();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void habilitarTodosMovimientos(){
        recyclerViewTodosMovimientos.setVisibility(View.VISIBLE);
        recyclerViewAbonos.setVisibility(View.GONE);
        recyclerViewGastos.setVisibility(View.GONE);
        recyclerViewClientes.setVisibility(View.GONE);
        autoCompleteTextViewBuscar.setVisibility(View.GONE);
    }

    private void habilitarAbonos(){
        recyclerViewAbonos.setVisibility(View.VISIBLE);
        recyclerViewTodosMovimientos.setVisibility(View.GONE);
        recyclerViewGastos.setVisibility(View.GONE);
        recyclerViewClientes.setVisibility(View.GONE);
        autoCompleteTextViewBuscar.setVisibility(View.GONE);
    }

    private void habilitarGastos(){
        recyclerViewGastos.setVisibility(View.VISIBLE);
        recyclerViewTodosMovimientos.setVisibility(View.GONE);
        recyclerViewAbonos.setVisibility(View.GONE);
        recyclerViewClientes.setVisibility(View.GONE);
        autoCompleteTextViewBuscar.setVisibility(View.GONE);
    }

    private void habilitarClientes(){
        recyclerViewClientes.setVisibility(View.VISIBLE);
        recyclerViewTodosMovimientos.setVisibility(View.GONE);
        recyclerViewAbonos.setVisibility(View.GONE);
        recyclerViewGastos.setVisibility(View.GONE);
        autoCompleteTextViewBuscar.setVisibility(View.VISIBLE);
    }

}