package com.example.seccion_5_list_grid_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SimpleListView extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list_view);

        listView = (ListView) findViewById(R.id.listView);

        // Datos a mostrar en el listView
        List<String> listaNombres = new ArrayList<String>();
        listaNombres.add("Panda Rojo");
        listaNombres.add("Panda Verde");
        listaNombres.add("Panda Azul");
        listaNombres.add("Pato Rojo");
        listaNombres.add("Pato Verde");
        listaNombres.add("Pato Azul");

        // Adaptador, la forma visual en que se van a mostrar los datos
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaNombres);

        // Asignamos el Adaptador a nuestro listView
        listView.setAdapter(adaptador);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Puedo acceder al elemento de esta manera parent.getItemAtPosition(position)
                //position: lo puedo utilizar para la posición del elemento del arreglo que agregué en el adaptador
                Toast.makeText(SimpleListView.this, "Nombre: " + parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }
        });
    }
}