package com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Abono;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Cliente;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterRecyclerViewMainClientes extends RecyclerView.Adapter<AdapterRecyclerViewMainClientes.ViewHolder>{

    private List<Cliente> listaClientes;
    private int layout;
    private Activity activity;

    public AdapterRecyclerViewMainClientes(List<Cliente> listaClientes, int layout, Activity activity){
        this.listaClientes = listaClientes;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listaClientes.get(position));
        final int posicion = position;
        holder.ivRecyclerItemLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !listaClientes.get(posicion).getTelefonoCliente().isEmpty() ){

                }else{
                    Toast.makeText(activity.getApplicationContext(), "No hay teléfono registrado", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvRecyclerItemClienteNombreValue;
        public TextView tvRecyclerItemClienteFechaValue;
        public TextView tvRecyclerItemClienteMontoValue;
        public ImageView ivRecyclerItemLlamar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecyclerItemClienteNombreValue = (TextView) itemView.findViewById(R.id.tvRecyclerItemClienteNombreValue);
            tvRecyclerItemClienteFechaValue = (TextView) itemView.findViewById(R.id.tvRecyclerItemClienteFechaValue);
            tvRecyclerItemClienteMontoValue = (TextView) itemView.findViewById(R.id.tvRecyclerItemClienteMontoValue);
            ivRecyclerItemLlamar = (ImageView) itemView.findViewById(R.id.ivRecyclerItemLlamar);

        }

        public void bind(final Cliente cliente){
            String fecha = cliente.obtenerUltimoAbono().getFechaFormateada();
            String monto = String.valueOf(cliente.obtenerUltimoAbono().getMontoAbono());
            tvRecyclerItemClienteNombreValue.setText(cliente.getNombreCliente());
            tvRecyclerItemClienteFechaValue.setText(fecha);
            tvRecyclerItemClienteMontoValue.setText(monto);
        }

    }

}
