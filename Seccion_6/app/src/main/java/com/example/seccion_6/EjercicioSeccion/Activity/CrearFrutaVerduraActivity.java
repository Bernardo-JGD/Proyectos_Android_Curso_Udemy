package com.example.seccion_6.EjercicioSeccion.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.seccion_6.EjercicioSeccion.Adaptador.AdaptadorSpinnerCrearFV;
import com.example.seccion_6.EjercicioSeccion.Modelo.FrutaVerdura;
import com.example.seccion_6.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CrearFrutaVerduraActivity extends AppCompatActivity {

    private Spinner spCategoriaFV;
    private Spinner spNombreFV;
    private AdaptadorSpinnerCrearFV adaptadorSpinnerCategoria;
    private AdaptadorSpinnerCrearFV adaptadorSpinnerNombres;
    private String[] listaCategoriasFrutasVerduras;
    private String[] listaFrutasVerduras;
    private String categoríaSeleccionada;

    private EditText edCantidadFrutaVerduraAsignar;
    private EditText edPrecioFrutaVerduraAsignar;

    private Button btnCrearFrutaVerdura;

    private List<FrutaVerdura> listaFrutasVerdurasRecyclerView;
    private List<FrutaVerdura> listaFrutasVerdurasCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_fruta_verdura);

        listaFrutasVerdurasRecyclerView = new ArrayList<>();
        listaFrutasVerdurasCompra = new ArrayList<>();
        Intent intent = getIntent();
        recibiendoIntent(intent);

        /*Spinners*/
        spCategoriaFV = (Spinner) findViewById(R.id.spCategoriaFV);
        spNombreFV = (Spinner) findViewById(R.id.spNombreFV);
        listaCategoriasFrutasVerduras = getResources().getStringArray(R.array.categoriasFrutasVerduras);
        listaFrutasVerduras = getResources().getStringArray(R.array.stringArrayFrutas);

        adaptadorSpinnerCategoria = new AdaptadorSpinnerCrearFV(this, R.layout.spinner_fruta_verdura_item, listaCategoriasFrutasVerduras);
        spCategoriaFV.setAdapter(adaptadorSpinnerCategoria);

        adaptadorSpinnerNombres = new AdaptadorSpinnerCrearFV(this, R.layout.spinner_fruta_verdura_item, listaFrutasVerduras);
        spNombreFV.setAdapter(adaptadorSpinnerNombres);

        spCategoriaFV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoríaSeleccionada = parent.getItemAtPosition(position).toString();
                if(categoríaSeleccionada.equals("Frutas")){
                    listaFrutasVerduras = getResources().getStringArray(R.array.stringArrayFrutas);
                    adaptadorSpinnerNombres = new AdaptadorSpinnerCrearFV(CrearFrutaVerduraActivity.this, R.layout.spinner_fruta_verdura_item, listaFrutasVerduras);
                }else if(categoríaSeleccionada.equals("Verduras")){
                    listaFrutasVerduras = getResources().getStringArray(R.array.stringArrayVerduras);
                    adaptadorSpinnerNombres = new AdaptadorSpinnerCrearFV(CrearFrutaVerduraActivity.this, R.layout.spinner_fruta_verdura_item, listaFrutasVerduras);
                }
                spNombreFV.setAdapter(adaptadorSpinnerNombres);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        edCantidadFrutaVerduraAsignar = (EditText) findViewById(R.id.edCantidadFrutaVerduraAsignar);
        edPrecioFrutaVerduraAsignar = (EditText) findViewById(R.id.edPrecioFrutaVerduraAsignar);

        btnCrearFrutaVerdura = (Button) findViewById(R.id.btnCrearFrutaVerdura);

        btnCrearFrutaVerdura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cantidad = edCantidadFrutaVerduraAsignar.getText().toString().trim();
                String precio = edPrecioFrutaVerduraAsignar.getText().toString().trim();

                if(validarCampoCantidad(cantidad) && validarCampoPrecio(precio)){

                    int cantidadNumero = Integer.parseInt(cantidad);
                    int precioNumero = (int) Double.parseDouble(precio);
                    String nombre = spNombreFV.getSelectedItem().toString();

                    Toast.makeText(getApplicationContext(), "Fruta Creada", Toast.LENGTH_LONG).show();

                    FrutaVerdura frutaVerdura = new FrutaVerdura(nombre, categoríaSeleccionada, precioNumero, cantidadNumero, 0);

                    retornarFrutaVerduraCreada(frutaVerdura);

                }else{
                    Toast.makeText(getApplicationContext(), "Error en los datos", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean validarCampoCantidad(String cantidad){
        // Verifica si la cadena contiene solo dígitos
        return cantidad.matches("\\d+");
    }

    private boolean validarCampoPrecio(String precio){
        // Verifica si la cadena tiene el formato de número con o sin decimal
        // o si es un número entero solo
        return precio.matches("\\d+(\\.\\d+)?|\\d+");
    }

    private void retornarFrutaVerduraCreada(FrutaVerdura frutaVerdura){
        Intent intent = new Intent(CrearFrutaVerduraActivity.this, FrutaVerduraActivity.class);
        listaFrutasVerdurasRecyclerView.add(0, frutaVerdura);
        intent.putExtra("listaRecycler", (Serializable) listaFrutasVerdurasRecyclerView);
        startActivity(intent);
    }

    private void recibiendoIntent(Intent intent){
        if(intent != null){
            if(intent.hasExtra("listaRecycler")){
                if(intent.getSerializableExtra("listaRecycler") != null){
                    listaFrutasVerdurasRecyclerView = (List<FrutaVerdura>) intent.getSerializableExtra("listaRecycler");
                }
            }
        }
    }

}