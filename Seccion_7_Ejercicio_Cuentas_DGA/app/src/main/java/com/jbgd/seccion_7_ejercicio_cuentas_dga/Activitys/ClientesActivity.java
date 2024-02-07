package com.jbgd.seccion_7_ejercicio_cuentas_dga.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters.AdapterRecyclerViewClientesAbonos;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Abono;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Cliente;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.Sort;

public class ClientesActivity extends AppCompatActivity {

    //Realm y modelo
    private Realm realm;
    private Cliente cliente;
    private RealmList<Abono> listaAbonos;
    private RealmList<Abono> listaAbonosFiltrados;
    private int idCliente;

    //Filtro
    private CardView cardFiltroFechaInicial;
    private CardView cardFiltroFechaFinal;
    private TextView textViewFiltroFechaInicial;
    private TextView textViewFiltroFechaFinal;
    private TextView textViewTotalAbonosRango;
    private Button btnFiltrarAbonos;

    //Filtro - Seleccionar fechas
    private CalendarView calendarView;
    private String fechaInicial;
    private String fechaFinal;
    private Date dateInicial;
    private Date dateFinal;
    private SimpleDateFormat dateFormat;
    private AlertDialog.Builder builder;
    private View viewInflated;
    private int tipoFecha = 0;

    //Lista abonos
    private RecyclerView recyclerViewClientesAbonos;
    private AdapterRecyclerViewClientesAbonos adapterRecyclerViewClientesAbonos;
    private RecyclerView.LayoutManager layoutManagerClientesAbonos;

    private FloatingActionButton fabAgregarAbono;
    private Date dateAbonoInicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();


        //Filtro
        cardFiltroFechaInicial = (CardView) findViewById(R.id.cardFiltroFechaInicial);
        cardFiltroFechaFinal = (CardView) findViewById(R.id.cardFiltroFechaFinal);
        textViewFiltroFechaInicial = (TextView) findViewById(R.id.textViewFiltroFechaInicial);
        textViewFiltroFechaFinal = (TextView) findViewById(R.id.textViewFiltroFechaFinal);
        textViewTotalAbonosRango = (TextView) findViewById(R.id.textViewTotalAbonosRango);
        btnFiltrarAbonos = (Button) findViewById(R.id.btnFiltrarAbonos);
        dateFormat = new SimpleDateFormat("EEEE dd 'de' MMMM 'del' yyyy", new Locale("es", "ES"));
        inicializarFechas();

        //Recibiendo cliente
        if(getIntent().getExtras() != null && getIntent().getExtras().getInt("idClienteSeleccionado") >= 0){
            idCliente = getIntent().getExtras().getInt("idClienteSeleccionado");
            consultarCliente();
        }

        cardFiltroFechaInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoFecha = 1;
                seleccionarFechas();
            }
        });

        cardFiltroFechaFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoFecha = 2;
                seleccionarFechas();
            }
        });

        //Lista abonos
        recyclerViewClientesAbonos = (RecyclerView) findViewById(R.id.recyclerViewClientesAbonos);
        layoutManagerClientesAbonos = new LinearLayoutManager(this);
        recyclerViewClientesAbonos.setHasFixedSize(true);
        recyclerViewClientesAbonos.setItemAnimator(new DefaultItemAnimator());
        recyclerViewClientesAbonos.setLayoutManager(layoutManagerClientesAbonos);

        adapterRecyclerViewClientesAbonos = new AdapterRecyclerViewClientesAbonos(listaAbonos, this, R.layout.item_recyclerview_clientes_abonos);
        recyclerViewClientesAbonos.setAdapter(adapterRecyclerViewClientesAbonos);

        cliente.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel realmModel) {
                adapterRecyclerViewClientesAbonos.notifyDataSetChanged();
            }
        });

        //Realizar filtrado por fecha
        btnFiltrarAbonos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateFinal.after(dateInicial)){
                    refrescarListaAbonos();
                }else{
                    toastFechaNoValida();
                }
            }
        });

        //Agregar abono
        fabAgregarAbono = (FloatingActionButton) findViewById(R.id.fabAgregarAbono);
        fabAgregarAbono.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);

        fabAgregarAbono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogCrearAbono();
            }
        });

    }

    private void crearAbono(Abono abono){
        realm.beginTransaction();
        realm.copyToRealm(abono);
        cliente.getAbonosCliente().add(abono);
        realm.commitTransaction();
        refrescarListaAbonos();
    }

    private void consultarCliente() {
        cliente = realm.where(Cliente.class).equalTo("id", idCliente).findFirst();
        getSupportActionBar().setTitle("Abonos: " + cliente.getNombreCliente());

        //Duda pero a ver como jala
        //https://stackoverflow.com/questions/30115021/how-to-convert-realmresults-object-to-realmlist
        listaAbonos = new RealmList<Abono>();
        listaAbonos.addAll(cliente.getAbonosCliente().where().between("fechaAbono", dateInicial, dateFinal).findAll().sort("fechaAbono", Sort.DESCENDING));

        /*
        for(Abono abono : cliente.getAbonosCliente()){
            Date fecha = abono.getFechaAbono();
            if(fecha.before(dateFinal) && fecha.after(dateInicial)){
                listaAbonos.add(abono);
            }
        }
        */
    }

    private void refrescarListaAbonos(){
        listaAbonos.clear();
        listaAbonos.addAll(cliente.getAbonosCliente().where().between("fechaAbono", dateInicial, dateFinal).findAll().sort("fechaAbono", Sort.DESCENDING));
        adapterRecyclerViewClientesAbonos.notifyDataSetChanged();
    }

    private void inicializarFechas(){
        //Obtengo la fecha del día actual
        dateInicial = new Date();

        //Obtengo la fecha del ultimo día del mes
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateInicial);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        dateFinal = calendar.getTime();

        fechaInicial = dateFormat.format(dateInicial);
        fechaFinal = dateFormat.format(dateFinal);

        textViewFiltroFechaInicial.setText(fechaInicial);
        textViewFiltroFechaFinal.setText(fechaFinal);
    }

    private void seleccionarFechas(){
        builder = new AlertDialog.Builder(this);

        if(tipoFecha == 1){
            builder.setTitle("Fecha inicial");
        }else if(tipoFecha == 2){
            builder.setTitle("Fecha final");
        }

        builder.setMessage("Seleccione una fecha");

        viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_fecha, null);
        builder.setView(viewInflated);

        calendarView = viewInflated.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                obtenerFechasCalendarView(year, month, dayOfMonth);
            }
        });

        builder.setPositiveButton("Seleccionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mostrarFecha();
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

    private void obtenerFechasCalendarView(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date fechaSeleccionada = calendar.getTime();
        if(tipoFecha == 1){
            if(fechaSeleccionada.before(dateFinal)){
                dateInicial = fechaSeleccionada;
            }else{
                toastFechaNoValida();
            }
        }else if(tipoFecha == 2){
            if(fechaSeleccionada.after(dateInicial)){
                dateFinal = calendar.getTime();
            }else{
                toastFechaNoValida();
            }
        }

    }

    private void mostrarFecha(){
        if(tipoFecha == 1){
            fechaInicial = dateFormat.format(dateInicial);
            textViewFiltroFechaInicial.setText(fechaInicial);
        }else if(tipoFecha == 2){
            fechaInicial = dateFormat.format(dateFinal);
            textViewFiltroFechaFinal.setText(fechaInicial);
        }
    }

    private void toastFechaNoValida(){
        Toast.makeText(this, "Rango de fechas no valido", Toast.LENGTH_LONG).show();
    }

    private void alertDialogCrearAbono(){
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Registrar Abono");
        builder.setMessage("Ingrese los siguientes datos");

        viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_abono, null);
        builder.setView(viewInflated);

        final EditText editTextAbonoConceptoValor = viewInflated.findViewById(R.id.editTextAbonoConceptoValor);
        final EditText editTextAbonoDescripcionValor = viewInflated.findViewById(R.id.editTextAbonoDescripcionValor);
        CardView cardViewAbonoFechaButton = viewInflated.findViewById(R.id.cardViewAbonoFechaButton);
        TextView textViewAbonoFechaMostrar = viewInflated.findViewById(R.id.textViewAbonoFechaMostrar);
        final EditText editTextAbonoMontoValor = viewInflated.findViewById(R.id.editTextAbonoMontoValor);

        dateAbonoInicial = new Date();
        textViewAbonoFechaMostrar.setText(dateFormat.format(dateInicial));

        cardViewAbonoFechaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogCalendarFechaAbono(textViewAbonoFechaMostrar);
            }
        });

        builder.setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String concepto = editTextAbonoConceptoValor.getText().toString().trim();
                String descripcion = editTextAbonoDescripcionValor.getText().toString().trim();
                Date fecha = dateAbonoInicial;
                double monto = Double.parseDouble(editTextAbonoMontoValor.getText().toString().trim());
                if(true){//VALIDACIONES
                    Abono abono = new Abono();
                    abono.setConceptoAbono(concepto);
                    abono.setDescripcionAbono(descripcion);
                    abono.setFechaAbono(fecha);
                    abono.setMontoAbono(monto);
                    crearAbono(abono);
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

    private void alertDialogCalendarFechaAbono(TextView mostrarFecha){
        AlertDialog.Builder builderCalendar = new AlertDialog.Builder(this);
        builderCalendar.setTitle("Fecha Abono");
        builderCalendar.setMessage("Seleccione una fecha para el abono");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_fecha, null);
        builderCalendar.setView(viewInflated);

        calendarView = viewInflated.findViewById(R.id.calendarView);
        final Calendar calendar = Calendar.getInstance();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
            }
        });

        builderCalendar.setPositiveButton("Seleccionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dateAbonoInicial = calendar.getTime();
                mostrarFecha.setText(dateFormat.format(dateAbonoInicial));
            }
        });

        builderCalendar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builderCalendar.create();
        builderCalendar.show();

    }

}