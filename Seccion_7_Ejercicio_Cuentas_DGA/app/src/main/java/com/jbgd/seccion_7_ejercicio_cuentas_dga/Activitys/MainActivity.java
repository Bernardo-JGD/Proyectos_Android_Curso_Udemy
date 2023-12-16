package com.jbgd.seccion_7_ejercicio_cuentas_dga.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters.AdapterSpinnerTipoMovimiento;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Cliente;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Elementos Spinner
    private Spinner spinnerTipoMovimiento;
    private String[] listaMovimientos;
    private AdapterSpinnerTipoMovimiento adapterSpinnerTipoMovimiento;
    private String movimientoSeleccionado;

    //Elementos cliente seleccionado en Spinner
    private EditText editTextBuscar;
    private List<Cliente> listaClientesPrueba;

    //RecyclersViews
    private RecyclerView recyclerViewTodosMovimientos;
    private RecyclerView recyclerViewAbonos;
    private RecyclerView recyclerViewGastos;
    private RecyclerView recyclerViewClientes;

    //floatingActionButtonAgregar
    private FloatingActionButton floatingActionButtonAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerTipoMovimiento = (Spinner) findViewById(R.id.spinnerTipoMovimiento);
        editTextBuscar = (EditText) findViewById(R.id.editTextBuscar);

        recyclerViewTodosMovimientos = (RecyclerView) findViewById(R.id.recyclerViewTodosMovimientos);
        recyclerViewAbonos = (RecyclerView) findViewById(R.id.recyclerViewAbonos);
        recyclerViewGastos = (RecyclerView) findViewById(R.id.recyclerViewGastos);
        recyclerViewClientes = (RecyclerView) findViewById(R.id.recyclerViewClientes);

        floatingActionButtonAgregar = (FloatingActionButton) findViewById(R.id.floatingActionButtonAgregar);
        //Asi puedo cambiar el color de lo que contiene adentro el FloatingActionButton
        floatingActionButtonAgregar.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);

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

        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        floatingActionButtonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void habilitarTodosMovimientos(){
        recyclerViewTodosMovimientos.setVisibility(View.VISIBLE);
        recyclerViewAbonos.setVisibility(View.GONE);
        recyclerViewGastos.setVisibility(View.GONE);
        recyclerViewClientes.setVisibility(View.GONE);
        editTextBuscar.setVisibility(View.GONE);
    }

    private void habilitarAbonos(){
        recyclerViewAbonos.setVisibility(View.VISIBLE);
        recyclerViewTodosMovimientos.setVisibility(View.GONE);
        recyclerViewGastos.setVisibility(View.GONE);
        recyclerViewClientes.setVisibility(View.GONE);
        editTextBuscar.setVisibility(View.GONE);
    }

    private void habilitarGastos(){
        recyclerViewGastos.setVisibility(View.VISIBLE);
        recyclerViewTodosMovimientos.setVisibility(View.GONE);
        recyclerViewAbonos.setVisibility(View.GONE);
        recyclerViewClientes.setVisibility(View.GONE);
        editTextBuscar.setVisibility(View.GONE);
    }

    private void habilitarClientes(){
        recyclerViewClientes.setVisibility(View.VISIBLE);
        recyclerViewTodosMovimientos.setVisibility(View.GONE);
        recyclerViewAbonos.setVisibility(View.GONE);
        recyclerViewGastos.setVisibility(View.GONE);
        editTextBuscar.setVisibility(View.VISIBLE);
    }

    private void buscarClientes(String nombreNombreCortoTelefono){

    }

    private void pruebaAutoComplete(){

    }

}