package com.example.seccion_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private List<String> listaNombres;
    private RecyclerView recyclerViewNames;
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager layoutManager;
    private int contadorNames = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        llenarListaNombres();
        recyclerViewNames = (RecyclerView) findViewById(R.id.recyclerViewNames);
        layoutManager = new LinearLayoutManager(this);
        //Podemos usar tambien el layout con Grid. El 2 es el número de columnas
        //layoutManager = new GridLayoutManager(this, 2);
        //Podemos usar tambien otro layout parecido al Grid
        //layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        adaptador = new MiAdaptadorRecycler(listaNombres, R.layout.recycler_view_item, new MiAdaptadorRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                //Toast.makeText(RecyclerViewActivity.this, "Nombre: " + name + " --- Posicion: " + position, Toast.LENGTH_SHORT).show();
                deleteName(position);
            }
        });

        recyclerViewNames.setHasFixedSize(true);
        recyclerViewNames.setItemAnimator(new DefaultItemAnimator());

        recyclerViewNames.setLayoutManager(layoutManager);
        recyclerViewNames.setAdapter(adaptador);

    }

    private void llenarListaNombres(){
        listaNombres = new ArrayList<String>();
        for(int i = 0; i<20; i++){
            ++contadorNames;
            listaNombres.add("Nombre --- " + contadorNames);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.agregarNombre:
                addName(0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void addName(int posicion){
        listaNombres.add(posicion, "Nombre --- " +(++contadorNames));
        adaptador.notifyItemInserted(posicion);
        layoutManager.scrollToPosition(posicion);
    }

    private void deleteName(int position){
        listaNombres.remove(position);
        adaptador.notifyItemRemoved(position);
    }

}