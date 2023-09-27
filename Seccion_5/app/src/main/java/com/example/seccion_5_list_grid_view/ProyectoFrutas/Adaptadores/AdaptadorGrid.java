package com.example.seccion_5_list_grid_view.ProyectoFrutas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seccion_5_list_grid_view.ProyectoFrutas.Modelo.FrutaVerdura;
import com.example.seccion_5_list_grid_view.R;

import java.util.List;

public class AdaptadorGrid extends BaseAdapter {

    private Context contexto;
    private int layout;
    private List<FrutaVerdura> listaFrutasVerduras;

    public AdaptadorGrid(Context contexto, int layout, List<FrutaVerdura> listaFrutasVerduras){
        this.contexto = contexto;
        this.layout = layout;
        this.listaFrutasVerduras = listaFrutasVerduras;
    }

    @Override
    public int getCount() {
        return this.listaFrutasVerduras.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listaFrutasVerduras.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vista = convertView;

        LayoutInflater layoutInflater = LayoutInflater.from(this.contexto);
        vista = layoutInflater.inflate(this.layout, null);

        String frutaVerdura = ((FrutaVerdura) getItem(position)).getNombreFrutaVerdura();
        int imagenFrutaVerduraCodigo = ((FrutaVerdura) getItem(position)).getIcono();

        TextView nombreFrutaVerdura = vista.findViewById(R.id.tvNombreFrutaVerduraGrid);
        ImageView ivFrutaVerdura = vista.findViewById(R.id.ivFrutaVerduraGrid);

        nombreFrutaVerdura.setText(frutaVerdura);
        ivFrutaVerdura.setImageResource(imagenFrutaVerduraCodigo);

        return vista;
    }

    public void clear(){
        this.listaFrutasVerduras.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<FrutaVerdura> nuevaLista){
        this.listaFrutasVerduras = nuevaLista;
        notifyDataSetChanged();
    }
}

