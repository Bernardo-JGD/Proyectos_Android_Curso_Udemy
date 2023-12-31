package com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Abono;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Gasto;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.R;

import java.util.List;

public class AdapterRecyclerViewMainGastos extends RecyclerView.Adapter<AdapterRecyclerViewMainGastos.ViewHolder>{

    private List<Gasto> listaGastos;
    private Activity activity;
    private int layout;

    public AdapterRecyclerViewMainGastos(List<Gasto> listaGastos, int layout, Activity activity){
        this.listaGastos = listaGastos;
        this.activity = activity;
        this.layout = layout;
        //ordenarListaAbonos();
    }

    private void ordenarListaAbonos(){
        if(listaGastos.size() > 1){
            for(int i = 0; i<listaGastos.size(); i++){
                for(int j = 0; j<listaGastos.size() - i - 1; j++){
                    if(listaGastos.get(j).getFechaGasto().after(listaGastos.get(j+1).getFechaGasto())){
                        listaGastos.add(j, listaGastos.get(j + 1));
                        listaGastos.remove(j+2);
                    }
                }
            }
        }
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
        holder.bind(listaGastos.get(position));
    }

    @Override
    public int getItemCount() {
        return listaGastos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvRecyclerItemGastoConceptoValue;
        public TextView tvRecyclerItemGastoFechaValue;
        public TextView tvRecyclerItemGastoMontoValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecyclerItemGastoConceptoValue = (TextView) itemView.findViewById(R.id.tvRecyclerItemGastoConceptoValue);
            tvRecyclerItemGastoFechaValue = (TextView) itemView.findViewById(R.id.tvRecyclerItemGastoFechaValue);
            tvRecyclerItemGastoMontoValue = (TextView) itemView.findViewById(R.id.tvRecyclerItemGastoMontoValue);

        }

        public void bind(Gasto gasto){
            String montoGasto = String.valueOf(gasto.getMontoGasto());
            tvRecyclerItemGastoConceptoValue.setText(gasto.getConceptoGasto());
            tvRecyclerItemGastoFechaValue.setText(gasto.getFechaFormateada());
            tvRecyclerItemGastoMontoValue.setText("$ " + montoGasto);
        }

    }

}
