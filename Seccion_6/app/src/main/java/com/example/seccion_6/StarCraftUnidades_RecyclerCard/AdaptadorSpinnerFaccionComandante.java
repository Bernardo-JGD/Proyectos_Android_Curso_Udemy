package com.example.seccion_6.StarCraftUnidades_RecyclerCard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.seccion_6.R;

public class AdaptadorSpinnerFaccionComandante extends ArrayAdapter<String> {

    private String[] faccionComandantes;
    private int layout;
    private Context context;

    public AdaptadorSpinnerFaccionComandante(@NonNull Context context, int layout, @NonNull String[] faccionComandantes) {
        super(context, layout, faccionComandantes);
        this.faccionComandantes = faccionComandantes;
        this.layout = layout;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View vista;

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        vista = layoutInflater.inflate(this.layout, null);

        TextView textViewFaccionComandante = vista.findViewById(R.id.textViewFaccionComandante);
        textViewFaccionComandante.setText(faccionComandantes[position]);


        return vista;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View vista = convertView;
        if (vista == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            vista = layoutInflater.inflate(R.layout.spinner_faccion_comandante_item, null);
        }

        TextView textViewFaccionComandante = vista.findViewById(R.id.textViewFaccionComandante);
        textViewFaccionComandante.setText(faccionComandantes[position]);
        //Hice esto para no crear un item para la lista desplegable
        textViewFaccionComandante.setPadding(0, 10, 0, 10);

        return vista;
    }


}
