package com.jbgd.seccion_7_ejercicio_cuentas_dga.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters.AdapterRecyclerViewMainAbonos;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters.AdapterRecyclerViewMainClientes;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters.AdapterRecyclerViewMainGastos;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters.AdapterSpinnerTipoMovimiento;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Abono;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Cliente;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Gasto;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity  {

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
    private AlertDialog.Builder builder;
    private View viewInflated;

    private NumberPicker numberPickerFechaDia;
    private NumberPicker numberPickerFechaMes;
    private NumberPicker numberPickerFechaYear;
    private TextView textViewFechaMostrada;
    private Date fechaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //En este momento si no pongo la siguiente linea marca error, todavía no cre objetos en DB pero evita error
        Realm.init(getApplicationContext());
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
        listaGastos = realm.where(Gasto.class).findAll().sort("fechaGasto", Sort.DESCENDING);
        listaClientes = realm.where(Cliente.class).findAll();

        adapterRecyclerViewMainClientes = new AdapterRecyclerViewMainClientes(listaClientes, R.layout.item_recyclerview_main_clientes, this);
        recyclerViewClientes.setHasFixedSize(true);
        recyclerViewClientes.setItemAnimator(new DefaultItemAnimator());
        recyclerViewClientes.setLayoutManager(layoutManagerClientes);
        recyclerViewClientes.setAdapter(adapterRecyclerViewMainClientes);

        adapterRecyclerViewMainClientes.setOnItemClickListener(new AdapterRecyclerViewMainClientes.OnItemClickListener() {
            @Override
            public void OnItemClick(Cliente cliente) {
                goToAbonosClientes(cliente);
            }
        });

        listaClientes.addChangeListener(new RealmChangeListener<RealmResults<Cliente>>() {
            @Override
            public void onChange(RealmResults<Cliente> clientes) {
                adapterRecyclerViewMainClientes.notifyDataSetChanged();
            }
        });

        adapterRecyclerViewMainGastos = new AdapterRecyclerViewMainGastos(listaGastos, R.layout.item_recyclerview_main_gastos, this);
        recyclerViewGastos.setHasFixedSize(true);
        recyclerViewGastos.setItemAnimator(new DefaultItemAnimator());
        recyclerViewGastos.setLayoutManager(layoutManagerGastos);
        recyclerViewGastos.setAdapter(adapterRecyclerViewMainGastos);

        listaGastos.addChangeListener(new RealmChangeListener<RealmResults<Gasto>>() {
            @Override
            public void onChange(RealmResults<Gasto> gastos) {
                adapterRecyclerViewMainGastos.notifyDataSetChanged();
            }
        });

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
                if(movimientoSeleccionado.equals("Abonos")){

                }else if(movimientoSeleccionado.equals("Gastos")){
                    showAlertDialogCrearGasto();
                }else if(movimientoSeleccionado.equals("Clientes")){
                    showAlertDialogCrearCliente();
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

    private void crearCliente(Cliente cliente){
        realm.beginTransaction();
        realm.copyToRealm(cliente);
        realm.commitTransaction();
    }

    private void crearGasto(Gasto gasto){
        realm.beginTransaction();
        realm.copyToRealm(gasto);
        realm.commitTransaction();
    }

    private void showAlertDialogCrearCliente(){
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Registrar cliente");
        builder.setMessage("Ingrese los siguientes datos");

        viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_cliente, null);
        builder.setView(viewInflated);

        final EditText editTextNombreValor = viewInflated.findViewById(R.id.editTextNombreValor);
        final EditText editTextNombreCortoValor = viewInflated.findViewById(R.id.editTextNombreCortoValor);
        final EditText editTextTelefonoValor = viewInflated.findViewById(R.id.editTextTelefonoValor);
        final EditText editTextDireccionValor = viewInflated.findViewById(R.id.editTextDireccionValor);

        /*
        editTextTelefonoValor.addTextChangedListener(new TextWatcher() {
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
        */

        builder.setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Aplicar al nombre expresiones regulares cuando cree las validaciones
                //no debe admitir números ní símbolos raros, solo puntos y acentos
                String nombre = editTextNombreValor.getText().toString().trim();
                String nombreCorto = editTextNombreCortoValor.getText().toString().trim();
                String telefono = editTextTelefonoValor.getText().toString().trim();
                String direccion = editTextDireccionValor.getText().toString().trim();
                if(true){//Aquí va una validación de los campos
                    Cliente cliente = new Cliente();
                    cliente.setNombreCliente(nombre);
                    cliente.setNombreCortoCliente(nombreCorto);
                    cliente.setTelefonoCliente(telefono);
                    cliente.setDireccionCliente(direccion);
                    crearCliente(cliente);
                }

            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create();
        builder.show();

    }

    private void showAlertDialogCrearGasto(){
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Registrar Gasto");
        builder.setMessage("Ingrese los siguientes datos");

        viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_gasto, null);
        builder.setView(viewInflated);

        final EditText editTextConceptoGastoValor = viewInflated.findViewById(R.id.editTextConceptoGastoValor);
        final EditText editTextDescripcionGastoValor = viewInflated.findViewById(R.id.editTextDescripcionGastoValor);
        numberPickerFechaDia = viewInflated.findViewById(R.id.numberPickerFechaDia);
        numberPickerFechaMes = viewInflated.findViewById(R.id.numberPickerFechaMes);
        numberPickerFechaYear = viewInflated.findViewById(R.id.numberPickerFechaYear);
        textViewFechaMostrada = viewInflated.findViewById(R.id.textViewFechaMostrada);

        valoresPorDefectoFechaGasto();

        numberPickerFechaDia.setOnValueChangedListener(cambioDeFecha);
        numberPickerFechaMes.setOnValueChangedListener(cambioDeFecha);
        numberPickerFechaYear.setOnValueChangedListener(cambioDeFecha);

        final EditText editTextMontoGastoValor = viewInflated.findViewById(R.id.editTextMontoGastoValor);

        builder.setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String concepto = editTextConceptoGastoValor.getText().toString().trim();
                String descripcion = editTextDescripcionGastoValor.getText().toString().trim();
                //Aplicar al monto expresiones regulares cuando cree las validaciones
                String monto = editTextMontoGastoValor.getText().toString().trim();
                Date fecha = fechaSeleccionada;

                if(true){//validaciones
                    Gasto gasto = new Gasto();
                    gasto.setConceptoGasto(concepto);
                    gasto.setDescripcionGasto(descripcion);
                    gasto.setFechaGasto(fecha);
                    gasto.setMontoGasto(Double.parseDouble(monto));
                    crearGasto(gasto);
                }

            }
        });

        builder.create();
        builder.show();

    }

    private void valoresPorDefectoFechaGasto(){
        int yearActual = Calendar.getInstance().get(Calendar.YEAR);

        numberPickerFechaDia.setMinValue(1);
        numberPickerFechaDia.setMaxValue(31);
        numberPickerFechaDia.setValue(1);

        numberPickerFechaMes.setMinValue(1);
        numberPickerFechaMes.setMaxValue(12);
        numberPickerFechaMes.setValue(1);

        numberPickerFechaYear.setMinValue(2000);
        numberPickerFechaYear.setMaxValue(yearActual);
        numberPickerFechaYear.setValue(yearActual);

        configurarFechaMostrada(numberPickerFechaDia.getValue(), numberPickerFechaMes.getValue(), numberPickerFechaYear.getValue());
    }

    private final NumberPicker.OnValueChangeListener cambioDeFecha = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            configurarNumberPickerDias();
        }
    };

    private void configurarNumberPickerDias(){
        int dia = numberPickerFechaDia.getValue();
        int mes = numberPickerFechaMes.getValue();
        int year = numberPickerFechaYear.getValue();

        if(mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12){
            numberPickerFechaDia.setMaxValue(31);
        } else if(mes == 4 || mes == 6 || mes == 9 || mes == 11){
            if(dia == 31){
                numberPickerFechaDia.setValue(30);
                numberPickerFechaDia.setMaxValue(30);
            }
        } else if(year%4 == 0){
            numberPickerFechaDia.setMaxValue(29);
        }else if(year%4 != 0 && dia == 29){
            numberPickerFechaDia.setMaxValue(28);
            numberPickerFechaDia.setValue(28);
        }
        dia = numberPickerFechaDia.getValue();

        configurarFechaMostrada(dia, mes, year);
    }

    private void configurarFechaMostrada(int dia, int mes, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, mes-1, dia);

        fechaSeleccionada = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd 'de' MMMM 'del' yyyy", new Locale("es", "ES"));
        String fechaFormateada = dateFormat.format(fechaSeleccionada);
        textViewFechaMostrada.setText("Fecha: " + fechaFormateada);
    }

    private void goToAbonosClientes(Cliente cliente){
        Intent intent = new Intent(MainActivity.this, ClientesActivity.class);
        intent.putExtra("idClienteSeleccionado",  cliente.getId());
        startActivity(intent);
    }

}