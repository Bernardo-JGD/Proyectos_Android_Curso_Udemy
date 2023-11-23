package com.example.seccion_6.EjercicioSeccion.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.seccion_6.EjercicioSeccion.Adaptador.TicketAdaptaer;
import com.example.seccion_6.EjercicioSeccion.Modelo.FrutaVerdura;
import com.example.seccion_6.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TicketActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTicket;
    private TicketAdaptaer ticketAdaptaer;
    private RecyclerView.LayoutManager layoutManagerTicket;
    private List<FrutaVerdura> listaCompras;
    public static TextView tvTotalFinalValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        getSupportActionBar().setTitle("Ticket");

        listaCompras = new ArrayList<>();
        Intent intent = getIntent();
        recibirIntent(intent);

        recyclerViewTicket = (RecyclerView) findViewById(R.id.recyclerViewTicket);
        layoutManagerTicket = new LinearLayoutManager(this);
        tvTotalFinalValor = (TextView) findViewById(R.id.tvTotalFinalValor);

        ticketAdaptaer = new TicketAdaptaer(listaCompras, R.layout.item_ticket_recycler_view, this);

        recyclerViewTicket.setHasFixedSize(true);
        recyclerViewTicket.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTicket.setAdapter(ticketAdaptaer);
        recyclerViewTicket.setLayoutManager(layoutManagerTicket);

        tvTotalFinalValor.setText(String.valueOf(ticketAdaptaer.calcularTotal()));

    }

    private void recibirIntent(Intent intent){
        if(intent != null){
            if(intent.hasExtra("listaCompra")){
                if(intent.getSerializableExtra("listaCompra") != null){
                    listaCompras = (List<FrutaVerdura>) intent.getSerializableExtra("listaCompra");
                    listaCompras = listaCompras.stream()
                                        .filter(fv -> fv.getCantidadTomada() > 0)
                                        .collect(Collectors.toList());
                }
            }
        }
    }



}