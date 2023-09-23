package com.example.seccion_5_list_grid_view.ProyectoFrutas.Activitys;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.seccion_5_list_grid_view.ProyectoFrutas.Adaptadores.AdaptadorSpinnerCategoria;
import com.example.seccion_5_list_grid_view.R;

public class MainRegistrarFrutas extends AppCompatActivity {

    private Spinner spCategoria;
    private Spinner spListaCategoria;
    private String[] categorias;
    private String[] frutas;
    private String[] verduras;
    private String categoriaSeleccionada;
    private AdaptadorSpinnerCategoria adaptadorFrutasVerduras;

    private RadioGroup rgListGrid;
    private final int rbListId = R.id.rbListView;
    private final int rbGridId = R.id.rbGridView;
    private final int rbAmbos = R.id.rbListGrid;

    private ListView listViewFrutasVerduras;
    private GridView gridViewFrutasVerduras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registrar_frutas);

        getSupportActionBar().setTitle("Frutas y Verduras");

        //Spinners
        spListaCategoria = (Spinner) findViewById(R.id.spListaCategoria);
        spCategoria = (Spinner) findViewById(R.id.spCategoria);

        //RadioGroup y RadioButtons
        rgListGrid = (RadioGroup) findViewById(R.id.rgOpciones);
        rgListGrid.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });

        //Grid y listView
        listViewFrutasVerduras = (ListView) findViewById(R.id.listViewFrutasVerduras);
        gridViewFrutasVerduras = (GridView) findViewById(R.id.gridViewFrutasVerduras);

        ((LinearLayout.LayoutParams)listViewFrutasVerduras.getLayoutParams()).weight = 1f;

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