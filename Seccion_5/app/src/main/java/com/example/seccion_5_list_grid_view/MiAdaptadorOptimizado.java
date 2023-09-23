package com.example.seccion_5_list_grid_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MiAdaptadorOptimizado extends BaseAdapter{

    private Context contexto;
    private int layout;
    private List<String> listaNombres;

    public MiAdaptadorOptimizado(Context contexto, int layout, List<String> listaNombres){
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

        ViewHolder holder;

        //Esto va a ayudar a que el proceso de "findViewById(R.id.tvNombre)"
        //no se repita si ya está cargado el elemento, ya que es algo pesado
        if(convertView == null){
            //Nos enlazamos con la vista que va a ser rellenada con la lista
            //que recibimos en el constructor
            LayoutInflater layoutInflater = LayoutInflater.from(this.contexto);
            convertView = layoutInflater.inflate(this.layout, null);

            holder = new ViewHolder();
            holder.nombreTextView = (TextView) convertView.findViewById(R.id.tvNombre);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }


        //Obtenemos los elementos por posición de la lista
        //Opcion 1: Utilizando el método getItem(posicion)
        String nombreActual = String.valueOf(getItem(position));
        //Opcion 2: Utilizando la lista que recibo en el constructor
        String nombreA = listaNombres.get(position);

        holder.nombreTextView.setText(nombreA);

        //Retornamos la vista con las modificaciones realizadas
        return convertView;
    }

    static class ViewHolder{
        //Si tengo más elementos visuales, los agrego aquí
        private TextView nombreTextView;
    }
}



