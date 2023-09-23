package com.example.seccion_5_list_grid_view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class AdaptadorPrueba extends BaseAdapter {
    private Context contexto;
    private int layout;
    private List<Persona> listaPersonas;

    public AdaptadorPrueba(Context contexto, int layout, List<Persona> listaPersonas) {
        this.contexto = contexto;
        this.layout = layout;
        this.listaPersonas = listaPersonas;
    }

    @Override
    public int getCount() {
        return this.listaPersonas.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listaPersonas.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vista;

        LayoutInflater layoutInflater = LayoutInflater.from(this.contexto);
        vista = layoutInflater.inflate(this.layout, null);

        String nombre = ((Persona) getItem(position)).getNombre();
        int edad = ((Persona) getItem(position)).getEdad();

        TextView tvNombreValor = vista.findViewById(R.id.tvNombreValor);
        TextView tvEdadValor = vista.findViewById(R.id.tvEdadValor);
        RelativeLayout contenedor = vista.findViewById(R.id.contenedor);

        if(edad<18){
            contenedor.setBackgroundColor(Color.parseColor("#FF6666"));
        }else{
            contenedor.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        tvNombreValor.setText(nombre + "--------" + position);
        tvEdadValor.setText(String.valueOf(edad));

        return vista;
    }


}
