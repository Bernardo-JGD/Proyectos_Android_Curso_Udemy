package com.example.seccion_6.EjercicioSeccion.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seccion_6.EjercicioSeccion.Adaptador.FrutaVerduraAdapter;
import com.example.seccion_6.EjercicioSeccion.Modelo.FrutaVerdura;
import com.example.seccion_6.EjercicioSeccion.Modelo.FrutaVerduraClaves;
import com.example.seccion_6.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FrutaVerduraActivity extends AppCompatActivity {

    public static TextView tvCantidadTotalValor;
    public static TextView tvTotalPrecioValor;

    private RecyclerView recyclerViewFrutaVerdura;
    private RecyclerView.LayoutManager layoutManager;
    //Esta va a servir para mantener el recycler lleno, tomando en cuenta que
    //en otra activity podrán asignar una fruta nueva, por eso es static
    private List<FrutaVerdura> listaFrutasVerdurasRecyclerView;
    //TODO: chatgpt me había comentado algo al respecto de acceder a la lista del adaptador
    //List<Fruit> selectedFruitsList = adapter.selectedFruits;
    private FrutaVerduraAdapter adaptadorFrutasVerduras;

    private Button btnIrAgregarFruta;
    private Button btnIrGenerarTicket;

    private Map<String, Integer> listaIconos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruta_verdura);

        tvCantidadTotalValor = (TextView) findViewById(R.id.tvCantidadTotalValor);
        tvTotalPrecioValor = (TextView) findViewById(R.id.tvTotalPrecioValor);

        recyclerViewFrutaVerdura = (RecyclerView) findViewById(R.id.recyclerViewFrutaVerdura);
        listaFrutasVerdurasRecyclerView = new ArrayList<>();

        btnIrAgregarFruta = (Button) findViewById(R.id.btnIrAgregarFruta);
        btnIrGenerarTicket = (Button) findViewById(R.id.btnIrGenerarTicket);

        //Caso 1: No agrega fruta, se mantiene igual la lista actual
        //Caso 2: Agrega fruta, se recibe la lista actual, se recibe la fruta, y se actualiza la lista actual
        //Nota: En ambos casos tambien se recibe la lista de compra, y si está no está null
        //      se tiene que hacer el descuento de frutas/verduras que ya se han añadido

        listaIconos = new HashMap<>();

        Intent intent = getIntent();
        recibirIntent(intent);

        layoutManager = new LinearLayoutManager(this);
        adaptadorFrutasVerduras = new FrutaVerduraAdapter(listaFrutasVerdurasRecyclerView, R.layout.item_frutaverdura_recycler_view,this);

        recyclerViewFrutaVerdura.setHasFixedSize(true);
        recyclerViewFrutaVerdura.setItemAnimator(new DefaultItemAnimator());

        recyclerViewFrutaVerdura.setAdapter(adaptadorFrutasVerduras);
        recyclerViewFrutaVerdura.setLayoutManager(layoutManager);

        btnIrAgregarFruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrutaVerduraActivity.this, CrearFrutaVerduraActivity.class);
                intent.putExtra("listaRecycler", (Serializable) listaFrutasVerdurasRecyclerView);
                startActivity(intent);
            }
        });

        btnIrGenerarTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Solo filtro para saber si selecciono un producto, pero mando la lista del adaptador al otro activity
                List<FrutaVerdura> listaCompra =
                        adaptadorFrutasVerduras.getListaFrutasVerduras()
                                .stream()
                                .filter( producto -> producto.getCantidadTomada() > 0)
                                .collect(Collectors.toList());

                if(validarListaCompra(listaCompra)){
                    Intent intent = new Intent(FrutaVerduraActivity.this, TicketActivity.class);
                    intent.putExtra("listaCompra", (Serializable) adaptadorFrutasVerduras.getListaFrutasVerduras());
                    startActivity(intent);
                }else{
                    Toast.makeText(FrutaVerduraActivity.this, "No hay productos seleccionados", Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    private void recibirIntent(Intent intent){
        if(intent != null){
            if(intent.hasExtra("listaRecycler")){
                if(intent.getSerializableExtra("listaRecycler") != null){
                    listaFrutasVerdurasRecyclerView = (List<FrutaVerdura>) intent.getSerializableExtra("listaRecycler");

                    if(listaFrutasVerdurasRecyclerView.size()>0){
                        asingarImagen();
                    }

                }

            }
        }
    }

    private boolean validarListaCompra(List<FrutaVerdura> listaCompra) {
        if(listaCompra == null){
            return false;
        }
        if(listaCompra.size() == 0){
            return false;
        }
        return true;
    }

    private void asingarImagen(){

        inicializarIconos();
        List<FrutaVerduraClaves<String, String, Integer>>  clavesImagenes = new ArrayList<>();
        String[] frutasVerduras = getResources().getStringArray(R.array.stringArrayFrutas);
        for(String fruta: frutasVerduras){
           clavesImagenes.add(new FrutaVerduraClaves<>(fruta, "Fruta", listaIconos.get(fruta)));
        }
        frutasVerduras = getResources().getStringArray(R.array.stringArrayVerduras);
        for(String verdura: frutasVerduras){
           clavesImagenes.add(new FrutaVerduraClaves<>(verdura, "Verdura", listaIconos.get(verdura)));
        }
        for(FrutaVerdura frutaVerdura: listaFrutasVerdurasRecyclerView){
            for(FrutaVerduraClaves<String, String, Integer> fvClave : clavesImagenes){
                if(fvClave.getNombreFrutaVerdura() == frutaVerdura.getNombre() && fvClave.getCategoria() == frutaVerdura.getCategoria()){
                    frutaVerdura.setImagen(fvClave.getImagen());
                    break;
                }
            }
        }

    }

    private void inicializarIconos(){
        listaIconos.put("Arándano", R.mipmap.ic_arandano_round);
        listaIconos.put("Cereza", R.mipmap.ic_cereza_round);
        listaIconos.put("Fresa", R.mipmap.ic_fresa_round);
        listaIconos.put("Kiwi", R.mipmap.ic_kiwi_round);
        listaIconos.put("Limón", R.mipmap.ic_limon_round);
        listaIconos.put("Manzana", R.mipmap.ic_manzana_round);
        listaIconos.put("Aguacate", R.mipmap.ic_aguacate_round);
        listaIconos.put("Pera", R.mipmap.ic_pera_round);
        listaIconos.put("Piña", R.mipmap.ic_pina_round);
        listaIconos.put("Plátanos", R.mipmap.ic_platano_round);
        listaIconos.put("Naranja", R.mipmap.ic_naranja_round);
        listaIconos.put("Sandía", R.mipmap.ic_sandia_round);
        listaIconos.put("Uvas", R.mipmap.ic_uva_round);


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

}

/*
//En Java, para almacenar dos valores con una sola clave,
//puedes crear tu propia clase que represente una entrada de diccionario personalizada.
//Esta clase contendrá dos campos para los valores y un campo para la clave.
//Aquí tienes un ejemplo de cómo podrías crear una clase de entrada de diccionario:
public class Entry<K, V1, V2> {
    private K key;
    private V1 value1;
    private V2 value2;

    public Entry(K key, V1 value1, V2 value2) {
        this.key = key;
        this.value1 = value1;
        this.value2 = value2;
    }

    public K getKey() {
        return key;
    }

    public V1 getValue1() {
        return value1;
    }

    public V2 getValue2() {
        return value2;
    }
}

List<Entry<String, Integer, String>> dictionary = new ArrayList<>();

Entry<String, Integer, String> entry1 = new Entry<>("clave1", 42, "valor1");
Entry<String, Integer, String> entry2 = new Entry<>("clave2", 123, "valor2");

dictionary.add(entry1);
dictionary.add(entry2);

// Para acceder a los valores
String clave = dictionary.get(0).getKey();
int valor1 = dictionary.get(0).getValue1();
String valor2 = dictionary.get(0).getValue2();


* */