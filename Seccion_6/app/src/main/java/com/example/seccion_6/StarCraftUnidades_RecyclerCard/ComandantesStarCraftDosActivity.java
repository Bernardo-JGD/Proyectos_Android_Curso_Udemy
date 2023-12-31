package com.example.seccion_6.StarCraftUnidades_RecyclerCard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
//Cuando Creo un paquete que incluya clase y activity dentro
//hacer este import de abajo, porque no detecta el layout
import com.example.seccion_6.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComandantesStarCraftDosActivity extends AppCompatActivity {

    private Map<String, Integer> listaImagenes;
    private Map<String, String> listaFrases;
    private Map<String, String> listaDescripcion;
    private List<Comandante> listaComandantes;
    private List<Comandante> listaComandantesRecycler;
    private List<Comandante> listaComandantesRecyclerFiltrada;

    private AdaptadorSpinnerFaccionComandante adaptadorSpinnerFacciones;
    private AdaptadorSpinnerFaccionComandante adaptadorSpinnerComandantes;
    private Spinner spinnerFaccion;
    private Spinner spinnerComandante;
    private Button btnAgregarComandante;
    private RecyclerView recyclerViewComandantes;
    private RecyclerViewComandantesAdapter adapterRecyclerViewComandantes;
    private RecyclerView.LayoutManager layoutManager;

    private String faccionSeleccionada;
    private String comandanteSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comandantes_star_craft_dos);

        getSupportActionBar().setTitle("Comandantes SC2");

        spinnerFaccion = (Spinner) findViewById(R.id.spinnerFaccion);
        spinnerComandante = (Spinner) findViewById(R.id.spinnerComandante);
        btnAgregarComandante = (Button) findViewById(R.id.btnAgregarComandante);
        recyclerViewComandantes = (RecyclerView) findViewById(R.id.recyclerViewComandantes);
        layoutManager = new LinearLayoutManager(this);

        listaImagenes = new HashMap<>();
        llenarListaImagenes();

        listaFrases = new HashMap<>();
        llenarListaFrases();

        listaDescripcion = new HashMap<>();
        llenarListaDescripcion();

        String[] facciones = {"Terran", "Protos", "Zerg"};

        adaptadorSpinnerFacciones = new AdaptadorSpinnerFaccionComandante(this, R.layout.spinner_faccion_comandante_item, facciones);
        spinnerFaccion.setAdapter(adaptadorSpinnerFacciones);

        listaComandantes = new ArrayList<>();
        llenarLstaComandantesBase();
        listaComandantesRecycler = new ArrayList<>();
        listaComandantesRecyclerFiltrada = new ArrayList<>();

        spinnerFaccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                faccionSeleccionada = parent.getItemAtPosition(position).toString();

                String[] comandantesFiltrados = listaComandantes.stream()
                        .filter(comandante -> comandante.getFaccion().equals(faccionSeleccionada))
                        .map(Comandante::getNombre)
                        .toArray(String[]::new);

                adaptadorSpinnerComandantes = new AdaptadorSpinnerFaccionComandante(ComandantesStarCraftDosActivity.this, R.layout.spinner_faccion_comandante_item, comandantesFiltrados);
                spinnerComandante.setAdapter(adaptadorSpinnerComandantes);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerComandante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                comandanteSeleccionado = parent.getItemAtPosition(position).toString();
                actualizarListaFiltrada();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Observa como pasamos el activity, con this. Podríamos declarar
        // Activity o Context en el constructor y funcionaría pasando el mismo valor, this
        adapterRecyclerViewComandantes = new RecyclerViewComandantesAdapter(listaComandantesRecyclerFiltrada, R.layout.comandante_recycler_view_item, this, new RecyclerViewComandantesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Comandante comandante, int posicion) {
                //Toast.makeText(ComandantesStarCraftDosActivity.this, comandante.getNombre(), Toast.LENGTH_LONG).show();
                //adapterRecyclerViewComandantes.notifyItemChanged(posicion);
            }
        });

        btnAgregarComandante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Opcion 1 para insertar (Propia, inserta al final)
                ////Aquí modifico primero el listaComandantesRecycler y luego actualizo la lista filtrada
                //Si no existe se puede agregar a la lista
                if( !ExistenciaComandante() ){
                    Comandante comandanteNuevo = encontrarComandante();
                    if(comandanteNuevo != null){
                        listaComandantesRecycler.add(comandanteNuevo);
                        actualizarListaFiltrada();
                    }
                }

                /*
                //Opcion 2 para insertar
                //Aquí modifico directamente la listaFiltrada
                if(!ExistenciaComandante()){
                    Comandante comandanteNuevo = encontrarComandante();
                    int posicion = 0;
                    if(comandanteNuevo != null){
                        listaComandantesRecyclerFiltrada.add(posicion, comandanteNuevo);
                        adapterRecyclerViewComandantes.notifyItemInserted(posicion);
                        layoutManager.scrollToPosition(posicion);
                    }
                }
                */

            }

            public boolean ExistenciaComandante(){
                for(Comandante comandante : listaComandantesRecycler){
                    if(comandante.getNombre().equals(comandanteSeleccionado)){
                        //Si existe
                        return true;
                    }
                }
                //Si no existe
                return false;
            }

            public Comandante encontrarComandante(){
                for(Comandante comandante : listaComandantes){
                    if(comandante.getNombre().equals(comandanteSeleccionado)){
                        return comandante;
                    }
                }
                return null;
            }

        });

        recyclerViewComandantes.setHasFixedSize(true);
        recyclerViewComandantes.setItemAnimator(new DefaultItemAnimator());

        recyclerViewComandantes.setLayoutManager(layoutManager);
        recyclerViewComandantes.setAdapter(adapterRecyclerViewComandantes);

    }

    private void llenarListaImagenes(){
        listaImagenes.put("Zeratul", R.mipmap.ic_zeratul);
        listaImagenes.put("Abathur", R.mipmap.ic_abathur);
        listaImagenes.put("Alarak", R.mipmap.ic_alarak);
        listaImagenes.put("Dehaka", R.mipmap.ic_dehaka);
        listaImagenes.put("Fénix", R.mipmap.ic_fenix);
        listaImagenes.put("Han y Horner", R.mipmap.ic_han_y_horner);
        listaImagenes.put("Mengsk", R.mipmap.ic_mensk);
        listaImagenes.put("Nova", R.mipmap.ic_nova);
        listaImagenes.put("Stetmann", R.mipmap.ic_stetmann);
        listaImagenes.put("Stukov", R.mipmap.ic_stukov);
        listaImagenes.put("Tychus", R.mipmap.ic_tychus);
    }

    private void llenarListaFrases(){
        listaFrases.put("Zeratul", "Mi vida por Aiur");
        listaFrases.put("Abathur", "Soy mi propia prole");
        listaFrases.put("Alarak", "Venga a los traicionados");
        listaFrases.put("Dehaka", "Accede a la mente de la colmena");
        listaFrases.put("Fénix", "Una leyenda que renace");
        listaFrases.put("Han y Horner", "Dos comandantes en uno");
        listaFrases.put("Mengsk", "Gobierna con mano de hierro");
        listaFrases.put("Nova", "Juegos mentales");
        listaFrases.put("Stetmann", "Genio adicto al terrazine");
        listaFrases.put("Stukov", "Poco ortodoxo... pero efectivo");
        listaFrases.put("Tychus", "Eh... ¡Ya está bien de esperar!");
    }

    private void llenarListaDescripcion(){
        listaDescripcion.put("Zeratul", "Zeratul es miembro de los templarios tétricos, un grupo de guerreros psiónicos cuyos antepasados fueron exiliados de Aiur, el planeta natal de los protoss, hace más de mil años. Los templarios tétricos se negaron a unirse al Khala, la mente común que conecta a todos los protoss, para vivir como seres independientes. Como líder de los templarios tétricos, Zeratul ostenta el rango de prelado y ha resultado crucial para asegurar la supervivencia de los protoss desde los inicios de la amenaza zerg.");
        listaDescripcion.put("Abathur", "Lidera tu ejército con Abathur, un maestro de la evolución zerg famoso por adaptarse incluso al entorno más hostil. Evoluciona más que nunca desatando monstruosidades como el brutalisco y el leviatán, o controlando cepas mortales de zerg como los guardianes, las cucarachas y los asoladores. Tú eres el futuro del Enjambre.");
        listaDescripcion.put("Alarak", "Dirige a tu ejército como Alarak, el astuto Gran Señor de los tal'darim. Despedaza a los enemigos con toda su fuerza psiónica y sírvete de tus propias unidades para seguir en el combate. La venganza es toda tuya.");
        listaDescripcion.put("Dehaka", "El astuto líder de manada y zerg primigenio Dehaka ha sobrevivido a muchas cosas a lo largo de los años, y todo eso lo ha hecho más fuerte. Aunque su gran motivación es el deseo de obtener más esencia, entablará alianzas cuando le convenga, y está dispuesto a ayudar a quienes sean merecedores de su sabiduría y perseverancia.");
        listaDescripcion.put("Fénix", "Ponte al mando de tu ejército con Fénix, un prototipo de guerrero purificador con una IA basada en uno de los héroes protoss más famosos. Equipado con un arsenal de armaduras, Fénix entra en batalla con un ejército mecánico y una vanguardia de campeones de IA conocidos por sus valientes hazañas. Reúne un ejército de leyendas y purifica el campo de batalla.");
        listaDescripcion.put("Han y Horner", "Matt Horner es un almirante del Dominio terran. Mira Han es una líder mercenaria que ha sido sentenciada a muerte en doce sistemas estelares. Unidos en matrimonio por una fatídica partida de póquer, ahora se reúnen de forma inesperada para combinar sus fuerzas... y resolver algunos problemas de su relación.");
        listaDescripcion.put("Mengsk", "Como líder de los Hijos de Korhal, Arcturus Mengsk provocó la destrucción de la Confederación terran y pasó a tener el Dominio terran en sus despiadadas manos. Como comandante cooperativo, exuda intimidación y tiranía para ejecutar su voluntad con total impunidad.");
        listaDescripcion.put("Nova", "Ponte al mando de tu ejército con Nova, una supersoldado fantasma terran equipada con maquinaria de tecnología avanzada y parte de la tecnología del ejército más impresionante del Dominio. Ábrete camino hacia la victoria con tus compañeros fantasmas, vence a tus enemigos con bombardeos contundentes o acéchalos en modo sigilo. Ponte al mando de unidades mejoradas y poderosas que debilitarán a tus enemigos más y más. La victoria te espera.");
        listaDescripcion.put("Stetmann", "Excéntrico, paranoico y con tendencia a sufrir alucinaciones bajo los efectos del terrazine, Egon Stetmann fue en su día el consejero científico jefe a bordo del Hyperion. El aislamiento extremo y la constante exposición al gas han hecho mella en la cordura de Stetmann, pero su mente sigue siendo tan aguda (y peligrosa) como siempre.");
        listaDescripcion.put("Stukov", "Ponte al mando de tu ejército con Alexei Stukov, el vicealmirante de la flota expedicionaria del Directorio de la Unión Terrestre infestada por el Enjambre. Con unidades y estructuras terran y zerg a tu disposición, la victoria está a tu alcance.");
        listaDescripcion.put("Tychus", "En este hipotético escenario podrás liderar a tu ejército como Tychus, un comandante que puede personalizar sus armas y reclutar a un equipo de forajidos formado por unidades heroicas en lugar de tradicionales. La nostalgia de sus años mozos ha llevado a Tychus a reunir a su vieja banda, los Diablos del Cielo, constituida por ocho de sus mejores colegas. Solo puedes reclutar a cuatro a la vez, así que elígelos bien para cada tarea.");
    }

    private void llenarLstaComandantesBase(){
        listaComandantes.add(new Comandante("Zeratul", "Protos", listaFrases.get("Zeratul"), listaDescripcion.get("Zeratul"), listaImagenes.get("Zeratul")));
        listaComandantes.add(new Comandante("Abathur", "Zerg", listaFrases.get("Abathur"), listaDescripcion.get("Abathur"), listaImagenes.get("Abathur")));
        listaComandantes.add(new Comandante("Alarak", "Protos", listaFrases.get("Alarak"), listaDescripcion.get("Alarak"), listaImagenes.get("Alarak")));
        listaComandantes.add(new Comandante("Dehaka", "Zerg", listaFrases.get("Dehaka"), listaDescripcion.get("Dehaka"), listaImagenes.get("Dehaka")));
        listaComandantes.add(new Comandante("Fénix", "Protos", listaFrases.get("Fénix"), listaDescripcion.get("Fénix"), listaImagenes.get("Fénix")));
        listaComandantes.add(new Comandante("Han y Horner", "Terran", listaFrases.get("Han y Horner"), listaDescripcion.get("Han y Horner"), listaImagenes.get("Han y Horner")));
        listaComandantes.add(new Comandante("Mengsk", "Terran", listaFrases.get("Mengsk"), listaDescripcion.get("Mengsk"), listaImagenes.get("Mengsk")));
        listaComandantes.add(new Comandante("Nova", "Terran", listaFrases.get("Nova"), listaDescripcion.get("Nova"), listaImagenes.get("Nova")));
        listaComandantes.add(new Comandante("Stetmann", "Terran", listaFrases.get("Stetmann"), listaDescripcion.get("Stetmann"), listaImagenes.get("Stetmann")));
        listaComandantes.add(new Comandante("Stukov", "Zerg", listaFrases.get("Stukov"), listaDescripcion.get("Stukov"), listaImagenes.get("Stukov")));
        listaComandantes.add(new Comandante("Tychus", "Terran", listaFrases.get("Tychus"), listaDescripcion.get("Tychus"), listaImagenes.get("Tychus")));
    }

    private void actualizarListaFiltrada(){
        listaComandantesRecyclerFiltrada = (List<Comandante>) listaComandantesRecycler.stream()
                .filter(
                        comandante -> comandante.getFaccion().equals(faccionSeleccionada)
                ).collect(Collectors.toList());

        ((RecyclerViewComandantesAdapter)adapterRecyclerViewComandantes).limpiarListaComandantes();
        ((RecyclerViewComandantesAdapter)adapterRecyclerViewComandantes).actualizarComandantes(listaComandantesRecyclerFiltrada);
    }


}