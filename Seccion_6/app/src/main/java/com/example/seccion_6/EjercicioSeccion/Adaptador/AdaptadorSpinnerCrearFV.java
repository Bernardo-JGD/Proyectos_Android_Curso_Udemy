package com.example.seccion_6.EjercicioSeccion.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.seccion_6.R;

import java.util.List;

public class AdaptadorSpinnerCrearFV extends ArrayAdapter<String> {

    private String[] fvOCategoria;
    private int layout;
    private Context context;

    public AdaptadorSpinnerCrearFV(@NonNull Context context, int layout, @NonNull String[] fvOCategoria) {
        super(context, layout, fvOCategoria);
        this.fvOCategoria = fvOCategoria;
        this.layout = layout;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vista;

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        vista = layoutInflater.inflate(this.layout, null);

        TextView tvSpinnerItem = vista.findViewById(R.id.tvSpinnerItem);
        tvSpinnerItem.setText(fvOCategoria[position]);


        return vista;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vista = convertView;
        if (vista == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            vista = layoutInflater.inflate(R.layout.spinner_fruta_verdura_item, null);
        }

        TextView tvSpinnerItem = vista.findViewById(R.id.tvSpinnerItem);
        tvSpinnerItem.setText(fvOCategoria[position]);
        //Hice esto para no crear un item para la lista desplegable en xml
        tvSpinnerItem.setPadding(0, 10, 0, 10);

        return vista;
    }

    public void setFvOCategoria(String[] fvOCategoria) {
        this.fvOCategoria = fvOCategoria;
    }
}
