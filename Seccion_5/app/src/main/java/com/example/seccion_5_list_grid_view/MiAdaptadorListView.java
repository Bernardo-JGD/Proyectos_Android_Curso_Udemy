package com.example.seccion_5_list_grid_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MiAdaptadorListView extends BaseAdapter {

    private Context contexto;
    private int layout;
    private List<String> listaNombres;

    public MiAdaptadorListView(Context contexto, int layout, List<String> listaNombres){
        this.contexto = contexto;
        this.layout = layout;
        this.listaNombres = listaNombres;
    }

    @Override
    public int getCount() {
        return this.listaNombres.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listaNombres.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vista = convertView;

        //Nos enlazamos con la vista que va a ser rellenada con la lista
        //que recibimos en el constructor
        LayoutInflater layoutInflater = LayoutInflater.from(this.contexto);
        vista = layoutInflater.inflate(this.layout, null);

        //Obtenemos los elementos por posición de la lista
        //Opcion 1: Utilizando el método getItem(posicion)
        String nombreActual = String.valueOf(getItem(position));
        //Opcion 2: Utilizando la lista que recibo en el constructor
        String nombreA = listaNombres.get(position);

        //Accedemos a los elementos de la vista y los manipulamos según necesitemos
        TextView tvNombre = vista.findViewById(R.id.tvNombre);
        tvNombre.setText(nombreActual);

        //Retornamos la vista con las modificaciones realizadas
        return vista;
    }
}
