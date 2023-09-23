package com.example.seccion_5_list_grid_view.ProyectoFrutas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.seccion_5_list_grid_view.AdaptadorPrueba;
import com.example.seccion_5_list_grid_view.R;

import java.util.List;

public class AdaptadorSpinnerCategoria extends ArrayAdapter<String> {

    private Context contexto;
    private int layout;
    private String[] categoriasFrutasVerduras;


    public AdaptadorSpinnerCategoria(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.layout = resource;
        this.categoriasFrutasVerduras = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        // Inflar el diseño personalizado del elemento del Spinner
        View vista;

        LayoutInflater layoutInflater = LayoutInflater.from(this.contexto);
        vista = layoutInflater.inflate(this.layout, null);

        TextView item = vista.findViewById(R.id.elementoSpinner);
        item.setText(getItem(position));

        return vista;
    }

}
