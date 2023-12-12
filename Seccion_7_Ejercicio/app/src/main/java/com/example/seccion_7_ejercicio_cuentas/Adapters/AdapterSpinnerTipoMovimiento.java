package com.example.seccion_7_ejercicio_cuentas.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.seccion_7_ejercicio_cuentas.R;

import java.util.List;

public class AdapterSpinnerTipoMovimiento extends ArrayAdapter<String> {

    private String[] listaMovimientos;
    private Context context;
    private int layout;

    public AdapterSpinnerTipoMovimiento(@NonNull Context context, int layout, @NonNull String[] listaMovimientos) {
        super(context, layout, listaMovimientos);
        this.listaMovimientos = listaMovimientos;
        this.context = context;
        this.layout = layout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent){
        View vista;

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        vista = layoutInflater.inflate(this.layout, null);

        TextView item = vista.findViewById(R.id.textViewItemSpinnerMovimiento);
        item.setText(getItem(position));

        return vista;
    }
}
