package com.example.seccion_5_list_grid_view.ProyectoFrutas.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.seccion_5_list_grid_view.ProyectoFrutas.Adaptadores.AdaptadorList;
import com.example.seccion_5_list_grid_view.ProyectoFrutas.Adaptadores.AdaptadorSpinnerCategoria;
import com.example.seccion_5_list_grid_view.ProyectoFrutas.Modelo.FrutaVerdura;
import com.example.seccion_5_list_grid_view.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainRegistrarFrutas extends AppCompatActivity {

    private Spinner spCategoria;
    private Spinner spListaCategoria;
    private String[] categorias;
    private String[] frutas;
    private String[] verduras;
    private String categoriaSeleccionada;
    private String frutaVerduraSeleccionada;
    private AdaptadorSpinnerCategoria adaptadorFrutasVerduras;
    private List<FrutaVerdura> listaFrutasVerduras;
    private Map<String, Integer> listaIconos;

    private RadioGroup rgListGrid;
    private final int rbListId = R.id.rbListView;
    private final int rbGridId = R.id.rbGridView;
    private final int rbAmbos = R.id.rbListGrid;

    private ListView listViewFrutasVerduras;
    private GridView gridViewFrutasVerduras;
    private AdaptadorList adaptadorList;

    private FloatingActionButton fabAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registrar_frutas);

        getSupportActionBar().setTitle("Frutas y Verduras");

        //Spinners
        spListaCategoria = (Spinner) findViewById(R.id.spListaCategoria);
        spCategoria = (Spinner) findViewById(R.id.spCategoria);

        //Grid y listView
        listViewFrutasVerduras = (ListView) findViewById(R.id.listViewFrutasVerduras);
        gridViewFrutasVerduras = (GridView) findViewById(R.id.gridViewFrutasVerduras);
        listaFrutasVerduras = new ArrayList<FrutaVerdura>();
        listaIconos = new HashMap<>();
        //Filtrar por categoria con streams y mandar así al adaptador
        //Crear una función que detecte el cambio de categoría y notificar al adaptador
        //adaptadorList = new AdaptadorList(this, R.layout.registrar_frutas_list_item, listaFrutasVerduras.stream().filter(frutaVerdura -> frutaVerdura.getCategoria() ))

        //Boton agregar
        fabAgregar = (FloatingActionButton) findViewById(R.id.fabAgregar);
        //Asi puedo cambiar el color de lo que contiene adentro el FloatingActionButton
        fabAgregar.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);

        //RadioGroup y RadioButtons
        rgListGrid = (RadioGroup) findViewById(R.id.rgOpciones);
        rgListGrid.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case rbListId:
                        habilitarListView();
                        break;

                    case rbGridId:
                        habilitarGridView();
                        break;

                    case rbAmbos:
                        habilitarAmbos();
                        break;
                }
            }

            private void habilitarListView(){
                listViewFrutasVerduras.setVisibility(View.VISIBLE);
                gridViewFrutasVerduras.setVisibility(View.GONE);
                //((LinearLayout.LayoutParams)listViewFrutasVerduras.getLayoutParams()).weight = 1f;
                //((LinearLayout.LayoutParams)gridViewFrutasVerduras.getLayoutParams()).weight = 0f;
            }

            private void habilitarGridView(){
                listViewFrutasVerduras.setVisibility(View.GONE);
                gridViewFrutasVerduras.setVisibility(View.VISIBLE);
            }

            private void habilitarAmbos(){
                listViewFrutasVerduras.setVisibility(View.VISIBLE);
                gridViewFrutasVerduras.setVisibility(View.VISIBLE);
            }
        });


        this.llenarArreglosSpinner();
        AdaptadorSpinnerCategoria adaptadorCategorias = new AdaptadorSpinnerCategoria(this, R.layout.frutas_spinner_item, this.categorias);
        spCategoria.setAdapter(adaptadorCategorias);


        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriaSeleccionada = parent.getItemAtPosition(position).toString();
                if(categoriaSeleccionada.equals("Frutas")){
                    adaptadorFrutasVerduras = new AdaptadorSpinnerCategoria(MainRegistrarFrutas.this, R.layout.frutas_spinner_item, frutas);
                    spListaCategoria.setAdapter(adaptadorFrutasVerduras);
                }else if(categoriaSeleccionada.equals("Verduras")){
                    adaptadorFrutasVerduras = new AdaptadorSpinnerCategoria(MainRegistrarFrutas.this, R.layout.frutas_spinner_item, verduras);
                    spListaCategoria.setAdapter(adaptadorFrutasVerduras);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        
        spListaCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                frutaVerduraSeleccionada = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fabAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobarFrutaVerdura()){

                    Toast.makeText(MainRegistrarFrutas.this, "Inserción realizada con éxito", Toast.LENGTH_SHORT).show();
                }
            }

            private boolean comprobarFrutaVerdura(){
                for (FrutaVerdura frutaVerdura: listaFrutasVerduras) {
                    if(categoriaSeleccionada.equals(frutaVerdura.getCategoria()) && frutaVerduraSeleccionada.equals(frutaVerdura.getNombreFrutaVerdura())){
                        return false;
                    }
                }
                insertarFrutaVerdura();
                return true;
            }
            private void insertarFrutaVerdura(){
                FrutaVerdura frutaVerdura = new FrutaVerdura();
                frutaVerdura.setCategoria(categoriaSeleccionada);
                frutaVerdura.setNombreFrutaVerdura(frutaVerduraSeleccionada);
                frutaVerdura.setIcono(listaIconos.get(frutaVerduraSeleccionada));
            }
        });

    }

    private void llenarArreglosSpinner(){
        this.categorias = new String[] {"Frutas", "Verduras"};

        this.frutas = new String[]{
            "Arándano",
            "Cereza",
            "Fresa",
            "Kiwi",
            "Limón",
            "Manzana",
            "Aguacate",
            "Pera",
            "Piña",
            "Plátanos",
            "Naranja",
            "Sandía",
            "Uvas",
        };

        this.verduras = new String[]{
            "Brócoli",
            "Calabaza",
            "Cebolla",
            "Maíz",
            "Papa",
            "Pimiento",
            "Repollo",
            "Tomate",
            "Zanahoria"

        };
    }

    private void inicializarIconos(){
        listaIconos.put("Arándano", R.mipmap.ic_arandano);
    }

    /* Trabajar el atributo height, width y weight, gracias chatGpt XD

    // Supongamos que tienes una referencia a una vista, por ejemplo, un TextView
    TextView textView = findViewById(R.id.miTextView);

    // Obtén los parámetros de diseño actuales de la vista
    ViewGroup.LayoutParams params = textView.getLayoutParams();

    // Accede a las propiedades de diseño que necesitas y modifícalas si es necesario
    params.width = ViewGroup.LayoutParams.MATCH_PARENT; // Cambia el ancho
    params.height = 200; // Cambia la altura en píxeles
    if (params instanceof LinearLayout.LayoutParams) {
        ((LinearLayout.LayoutParams) params).weight = 0.5f; // Cambia el layout_weight si es un LinearLayout
    }

    // Aplica los cambios a la vista
    textView.setLayoutParams(params);


    //************Padding xml
    Puedes agregar padding a un elemento en XML utilizando el atributo android:padding o sus variantes específicas
     como android:paddingLeft, android:paddingRight, android:paddingTop, y android:paddingBottom.




     */
}