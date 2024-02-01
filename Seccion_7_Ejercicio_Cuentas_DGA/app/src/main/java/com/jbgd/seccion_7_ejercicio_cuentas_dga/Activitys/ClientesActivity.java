package com.jbgd.seccion_7_ejercicio_cuentas_dga.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
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
    private CalendarView calendarViewFiltro;
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

        //Agregar abono
        fabAgregarAbono = (FloatingActionButton) findViewById(R.id.fabAgregarAbono);
        fabAgregarAbono.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);

        //Recibiendo cliente
        if(getIntent().getExtras() != null && getIntent().getExtras().getInt("Id") > 0){
            idCliente = getIntent().getExtras().getInt("Id");
            consultarCliente();
        }

        //Falta recibir id de cliente para consultarlo y extraer info
        //llenar actionbar, recyclerView
        //getSupportActionBar().setTitle("Abonos: " + cliente.getNombreCliente());

        fabAgregarAbono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void consultarCliente() {
        cliente = realm.where(Cliente.class).equalTo("id", idCliente).findFirst();
        cliente.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel realmModel) {

            }
        });
        listaAbonos = cliente.getAbonosCliente();
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

        viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_filtro_fecha, null);
        builder.setView(viewInflated);

        calendarViewFiltro = viewInflated.findViewById(R.id.calendarViewFiltro);

        calendarViewFiltro.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
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

        if(tipoFecha == 1){
            dateInicial = calendar.getTime();
        }else if(tipoFecha == 2){
            if(calendar.getTime().after(dateInicial)){
                dateFinal = calendar.getTime();
            }else{
                Toast.makeText(this, "Fecha menor, no valida", Toast.LENGTH_LONG).show();
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

}