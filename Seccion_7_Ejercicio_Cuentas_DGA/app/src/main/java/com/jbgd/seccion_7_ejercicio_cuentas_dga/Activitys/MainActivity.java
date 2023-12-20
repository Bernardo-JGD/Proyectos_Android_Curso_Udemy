package com.jbgd.seccion_7_ejercicio_cuentas_dga.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters.AdapterRecyclerViewMainAbonos;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters.AdapterRecyclerViewMainClientes;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters.AdapterRecyclerViewMainGastos;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters.AdapterSpinnerTipoMovimiento;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Abono;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Cliente;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Gasto;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.R;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

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

    //Adapters Recyclers
    private AdapterRecyclerViewMainAbonos adapterRecyclerViewMainAbonos;
    private AdapterRecyclerViewMainGastos adapterRecyclerViewMainGastos;
    private AdapterRecyclerViewMainClientes adapterRecyclerViewMainClientes;

    //LayoutManagers para los RecyclerViews
    private RecyclerView.LayoutManager layoutManagerAbonos;
    private RecyclerView.LayoutManager layoutManagerGastos;
    private RecyclerView.LayoutManager layoutManagerClientes;

    //Realm y listas
    private Realm realm;
    private RealmResults<Abono> listaAbonos;
    private RealmResults<Gasto> listaGastos;
    private RealmResults<Cliente> listaClientes;

    //floatingActionButtonAgregar
    private FloatingActionButton floatingActionButtonAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        spinnerTipoMovimiento = (Spinner) findViewById(R.id.spinnerTipoMovimiento);
        editTextBuscar = (EditText) findViewById(R.id.editTextBuscar);

        recyclerViewTodosMovimientos = (RecyclerView) findViewById(R.id.recyclerViewTodosMovimientos);
        recyclerViewAbonos = (RecyclerView) findViewById(R.id.recyclerViewAbonos);
        recyclerViewGastos = (RecyclerView) findViewById(R.id.recyclerViewGastos);
        recyclerViewClientes = (RecyclerView) findViewById(R.id.recyclerViewClientes);

        layoutManagerAbonos = new LinearLayoutManager(this);
        layoutManagerGastos = new LinearLayoutManager(this);
        layoutManagerClientes = new LinearLayoutManager(this);

        listaAbonos = realm.where(Abono.class).findAll();
        listaGastos = realm.where(Gasto.class).findAll();
        listaClientes = realm.where(Cliente.class).findAll();

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
                if(movimientoSeleccionado.equals("Todos")){

                }else if(movimientoSeleccionado.equals("Abonos")){

                }else if(movimientoSeleccionado.equals("Gastos")){

                }else if(movimientoSeleccionado.equals("Clientes")){

                }
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

    private void showAlertDialogCrearCliente(){

    }

    private void showAlertDialogCrearGasto(){

    }

    private void showAlertDialogCrearAbono(){

    }

}