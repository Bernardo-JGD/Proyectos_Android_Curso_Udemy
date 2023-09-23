package com.example.seccion_5_list_grid_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListViewPruebaActivity extends AppCompatActivity {

    private List<Persona> listaPersonas;
    private ListView listaPrueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_prueba);

        listaPrueba = (ListView) findViewById(R.id.listaPrueba);

        listaPersonas = new ArrayList<Persona>();
        listaPersonas.add(new Persona("Panda", 14));
        listaPersonas.add(new Persona("Cobra", 20));
        listaPersonas.add(new Persona("Grizzly", 24));
        listaPersonas.add(new Persona("Panda", 14));
        listaPersonas.add(new Persona("Cobra", 20));
        listaPersonas.add(new Persona("Grizzly", 24));
        listaPersonas.add(new Persona("Panda", 14));
        listaPersonas.add(new Persona("Cobra", 20));
        listaPersonas.add(new Persona("Grizzly", 24));
        listaPersonas.add(new Persona("Panda", 14));
        listaPersonas.add(new Persona("Cobra", 20));
        listaPersonas.add(new Persona("Grizzly", 24));
        listaPersonas.add(new Persona("Panda", 14));
        listaPersonas.add(new Persona("Cobra", 20));
        listaPersonas.add(new Persona("Grizzly", 24));

        AdaptadorPrueba adaptadorPrueba = new AdaptadorPrueba(this, R.layout.list_item_prueba, listaPersonas);
        listaPrueba.setAdapter(adaptadorPrueba);


    }



}

