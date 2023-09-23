package com.example.seccion_5_list_grid_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    private GridView gridView;
    private List<String> listaNombres;
    private MiAdaptadorListView adaptador;
    private int contadorElementosAgregados = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        gridView = (GridView) findViewById(R.id.gridView);

        // Datos a mostrar en el listView
        listaNombres = new ArrayList<String>();
        listaNombres.add("Panda Rojo");
        listaNombres.add("Panda Verde");
        listaNombres.add("Panda Azul");
        listaNombres.add("Pato Rojo");
        listaNombres.add("Pato Verde");
        listaNombres.add("Pato Azul");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Puedo acceder al elemento de esta manera parent.getItemAtPosition(position)
                //position: lo puedo utilizar para la posición del elemento del arreglo que agregué en el adaptador
                Toast.makeText(GridViewActivity.this, "Nombre: " + parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }
        });

        //MiAdaptadorOptimizado adaptador = new MiAdaptadorOptimizado(this, R.layout.grid_item, listaNombres);
        //gridView.setAdapter(adaptador);

        adaptador = new MiAdaptadorListView(this, R.layout.grid_item, listaNombres);
        gridView.setAdapter(adaptador);

        registerForContextMenu(gridView);
    }

    // Inflamos el layout del menu de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        //Se agrega el la opción que creamos para el action bar
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    // Manejamos eventos click en el menu de opciones
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.add_item:
                // Añadimos nuevo nombre
                this.listaNombres.add("Add N° " + (++contadorElementosAgregados));
                // Notificamos al adaptador del cambio producido
                this.adaptador.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Inflamos el layout del context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        // Mostramos en el título el nombre del elemento que vamos a eliminar
        menu.setHeaderTitle(this.listaNombres.get(info.position));
        //Se agrega el la opción que creamos para el action bar
        inflater.inflate(R.menu.context_menu, menu);
    }

    // Manejamos eventos click en el context menu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //Esto me va a dar información del elemento pulsado
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()){
            case R.id.delete_item:
                // Borramos item clickeado
                this.listaNombres.remove(info.position);
                // Notificamos al adaptador del cambio producido
                this.adaptador.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }
}