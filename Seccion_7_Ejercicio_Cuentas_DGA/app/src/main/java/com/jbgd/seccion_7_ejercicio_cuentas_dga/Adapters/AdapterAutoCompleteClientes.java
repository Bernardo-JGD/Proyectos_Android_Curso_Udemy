package com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Cliente;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.R;

import java.util.List;

import io.realm.RealmResults;

public class AdapterAutoCompleteClientes extends ArrayAdapter<Cliente> {

    private List<Cliente> listaClientes;
    private int layout;
    private Context context;

    public AdapterAutoCompleteClientes(@NonNull Context context, int layout, @NonNull List<Cliente> listaClientes) {
        super(context, layout, listaClientes);
        this.listaClientes = listaClientes;
        this.layout = layout;
        this.context = context;
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

    public View getCustomView(int position, View converView, ViewGroup parent){
        View vista;

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        vista = layoutInflater.inflate(this.layout, null);

        Cliente clienteSeleccionado = (Cliente) getItem(position);

        TextView textViewItemNombreCliente = vista.findViewById(R.id.textViewItemNombreCliente);
        TextView textViewItemTelefonoCliente = vista.findViewById(R.id.textViewItemTelefonoCliente);

        textViewItemNombreCliente.setText(clienteSeleccionado.getNombreCliente());
        textViewItemTelefonoCliente.setText(clienteSeleccionado.getTelefonoCliente());

        return vista;
    }

}
