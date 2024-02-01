package com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Abono;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.R;

import java.util.List;

public class AdapterRecyclerViewClientesAbonos extends RecyclerView.Adapter<AdapterRecyclerViewClientesAbonos.ViewHolder>{

    private List<Abono> listaAbonosCliente;
    private Activity activity;
    private int layout;

    public AdapterRecyclerViewClientesAbonos(List<Abono> listaAbonosCliente, Activity activity, int layout){
        this.listaAbonosCliente = listaAbonosCliente;
        this.activity = activity;
        this.layout = layout;
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
        Abono abonoCliente = listaAbonosCliente.get(position);
        holder.bind(abonoCliente);
    }

    @Override
    public int getItemCount() {
        return listaAbonosCliente.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewAbonoConcpetoValor;
        private TextView textViewAbonoFechaValor;
        private TextView textViewAbonoMontoValor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewAbonoConcpetoValor = (TextView) itemView.findViewById(R.id.textViewAbonoConcpetoValor);
            textViewAbonoFechaValor = (TextView) itemView.findViewById(R.id.textViewAbonoFechaValor);
            textViewAbonoMontoValor = (TextView) itemView.findViewById(R.id.textViewAbonoMontoValor);

        }

        public void bind(Abono abonoCliente){
            textViewAbonoConcpetoValor.setText(abonoCliente.getConceptoAbono());
            textViewAbonoFechaValor.setText(abonoCliente.getFechaFormateada());
            textViewAbonoMontoValor.setText(abonoCliente.getMontoToString());
        }

    }

}
