package com.example.seccion_5_list_grid_view.ProyectoFrutas.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import com.example.seccion_5_list_grid_view.ProyectoFrutas.Adaptadores.AdaptadorGrid;
import com.example.seccion_5_list_grid_view.ProyectoFrutas.Adaptadores.AdaptadorList;
import com.example.seccion_5_list_grid_view.ProyectoFrutas.Adaptadores.AdaptadorSpinnerCategoria;
import com.example.seccion_5_list_grid_view.ProyectoFrutas.Modelo.FrutaVerdura;
import com.example.seccion_5_list_grid_view.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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
    private List<FrutaVerdura> listaFrutasVerdurasFiltrada;
    private Map<String, Integer> listaIconos;

    private RadioGroup rgListGrid;
    private final int rbListId = R.id.rbListView;
    private final int rbGridId = R.id.rbGridView;
    private final int rbAmbos = R.id.rbListGrid;

    private ListView listViewFrutasVerduras;
    private GridView gridViewFrutasVerduras;
    private AdaptadorList adaptadorList;
    private AdaptadorGrid adaptadorGrid;

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
        listaFrutasVerdurasFiltrada = new ArrayList<FrutaVerdura>();
        listaIconos = new HashMap<>();
        inicializarIconos();
        //Filtrar por categoria con streams y mandar así al adaptador
        //Crear una función que detecte el cambio de categoría y notificar al adaptador
        adaptadorList = new AdaptadorList(this, R.layout.registrar_frutas_list_item, listaFrutasVerdurasFiltrada);
        adaptadorGrid = new AdaptadorGrid(this, R.layout.registrar_frutas_grid_item, listaFrutasVerdurasFiltrada);
        listViewFrutasVerduras.setAdapter(adaptadorList);
        gridViewFrutasVerduras.setAdapter(adaptadorGrid);

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
                actualizarListaFiltrada();
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
                    //Avisamos que ya agregamos otra fruta o verdura
                    Toast.makeText(MainRegistrarFrutas.this, "Inserción realizada con éxito", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainRegistrarFrutas.this, "Ese elemento ya se registro", Toast.LENGTH_SHORT).show();
                }
            }

            private boolean comprobarFrutaVerdura(){
                for (FrutaVerdura frutaVerdura: listaFrutasVerduras) {
                    if(categoriaSeleccionada.equals(frutaVerdura.getCategoria()) && frutaVerduraSeleccionada.equals(frutaVerdura.getNombreFrutaVerdura())){
                        //Si ya existe
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
                listaFrutasVerduras.add(frutaVerdura);

                actualizarListaFiltrada();
            }
        });


        registerForContextMenu(listViewFrutasVerduras);
        registerForContextMenu(gridViewFrutasVerduras);
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
        //Frutas
        listaIconos.put("Arándano", R.mipmap.ic_arandano_round);
        listaIconos.put("Cereza", R.mipmap.ic_cereza_round);
        listaIconos.put("Fresa", R.mipmap.ic_fresa_round);
        listaIconos.put("Kiwi", R.mipmap.ic_kiwi_round);
        listaIconos.put("Limón", R.mipmap.ic_limon_round);
        listaIconos.put("Manzana", R.mipmap.ic_manzana_round);
        listaIconos.put("Aguacate", R.mipmap.ic_aguacate_round);
        listaIconos.put("Pera", R.mipmap.ic_pera_round);
        listaIconos.put("Piña", R.mipmap.ic_pina_round);
        listaIconos.put("Plátanos", R.mipmap.ic_platanos_round);
        listaIconos.put("Naranja", R.mipmap.ic_naranja_round);
        listaIconos.put("Sandía", R.mipmap.ic_sandia_round);
        listaIconos.put("Uvas", R.mipmap.ic_uvas_round);

        //Verduras
        listaIconos.put("Brócoli", R.mipmap.ic_brocoli_round);
        listaIconos.put("Calabaza", R.mipmap.ic_calabaza_round);
        listaIconos.put("Cebolla", R.mipmap.ic_cebolla_round);
        listaIconos.put("Maíz", R.mipmap.ic_maiz_round);
        listaIconos.put("Papa", R.mipmap.ic_papa_round);
        listaIconos.put("Pimiento", R.mipmap.ic_pimiento_round);
        listaIconos.put("Repollo", R.mipmap.ic_repollo_round);
        listaIconos.put("Tomate", R.mipmap.ic_tomate_round);
        listaIconos.put("Zanahoria", R.mipmap.ic_zanahoria_round);
    }

    private void actualizarListaFiltrada(){
        listaFrutasVerdurasFiltrada =(List<FrutaVerdura>) listaFrutasVerduras.stream()
                .filter(
                        frutaVerdura -> frutaVerdura.getCategoria().equals(categoriaSeleccionada)
                ).collect(Collectors.toList());

        adaptadorList.clear();
        adaptadorList.addAll(listaFrutasVerdurasFiltrada);
        adaptadorList.notifyDataSetChanged();

        adaptadorGrid.clear();
        adaptadorGrid.addAll(listaFrutasVerdurasFiltrada);
        adaptadorGrid.notifyDataSetChanged();
    }

    private void insercionAleatoria(){
        if(listaFrutasVerduras.size() != (frutas.length + verduras.length)){
            do{
                FrutaVerdura frutaVerdura;
                Random random = new Random();
                int categoria = random.nextInt(2);// Genera 0 o 1

                String[] arreglo = (categoria == 0) ? frutas : (categoria == 1) ? verduras : null;

                if(arreglo != null){
                    int posicion = random.nextInt(arreglo.length-1);
                    String nombreFrutaVerdura = arreglo[posicion];

                    frutaVerdura = asignarFruta(nombreFrutaVerdura, categoria == 0 ? "Frutas": "Verduras");
                    if(frutaVerdura != null){
                        //Cuando asigne una fruta valida
                        listaFrutasVerduras.add(frutaVerdura);
                        //Hago que la lista actual detecte el cambio si corresponde
                        actualizarListaFiltrada();
                        //Rompe el do while
                        break;
                    }
                }

            }while(true);
        }else{
            Toast.makeText(this, "No hay más frutas o verduras", Toast.LENGTH_LONG).show();
        }

    }

    private FrutaVerdura asignarFruta(String nombre, String categoria){
        for(FrutaVerdura frutaVerdura : listaFrutasVerduras){
            if(frutaVerdura.getNombreFrutaVerdura().equals(nombre)){
                return null;
            }
        }
        return new FrutaVerdura(nombre, categoria, listaIconos.get(nombre));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu_fruta_random, menu);
        //super.onCreateOptionsMenu(menu)
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.abmAgregarFrutaVerduraRandom:
                //insercionAleatoria()
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(this.listaFrutasVerdurasFiltrada.get(info.position).getNombreFrutaVerdura());

        inflater.inflate(R.menu.context_menu_frutas_verduras, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()){
            case R.id.eliminarFrutaVerdura:
                FrutaVerdura frutaVerdura = listaFrutasVerdurasFiltrada.get(info.position);
                listaFrutasVerduras.remove(frutaVerdura);
                actualizarListaFiltrada();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

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