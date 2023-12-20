package com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Abono;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Cliente;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.R;

import java.util.List;

public class AdapterRecyclerViewMainAbonos extends RecyclerView.Adapter<AdapterRecyclerViewMainAbonos.ViewHolder>{

    private List<Abono> listaAbonos;
    private List<Cliente> listaClientes;
    private Activity activity;
    private int layout;

    public AdapterRecyclerViewMainAbonos(List<Abono> listaAbonos, List<Cliente> listaClientes, Activity activity, int layout){
        this.listaAbonos = listaAbonos;
        this.listaClientes = listaClientes;
        this.activity = activity;
        this.layout = layout;
        ordenarListaAbonos();
    }

    private void ordenarListaAbonos(){
        if(listaAbonos.size() > 1){
            for(int i = 0; i<listaAbonos.size(); i++){
                for(int j = 0; j<listaAbonos.size() - i - 1; j++){
                    if(listaAbonos.get(j).getFechaAbono().after(listaAbonos.get(j+1).getFechaAbono())){
                        Abono abonoTemporal = listaAbonos.get(j);
                        listaAbonos.set(j, listaAbonos.get(j+1));
                        listaAbonos.set(j+1, abonoTemporal);
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
        holder.bind(listaAbonos.get(position), listaClientes);
    }

    @Override
    public int getItemCount() {
        return listaAbonos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvRecyclerItemAbonoNombreValue;
        public TextView tvRecyclerItemAbonoConceptoValue;
        public TextView tvRecyclerItemAbonoFechaValue;
        public TextView tvRecyclerItemAbonoMontoValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecyclerItemAbonoNombreValue = (TextView) itemView.findViewById(R.id.tvRecyclerItemAbonoNombreValue);
            tvRecyclerItemAbonoConceptoValue = (TextView) itemView.findViewById(R.id.tvRecyclerItemAbonoConceptoValue);
            tvRecyclerItemAbonoFechaValue = (TextView) itemView.findViewById(R.id.tvRecyclerItemAbonoFechaValue);
            tvRecyclerItemAbonoMontoValue = (TextView) itemView.findViewById(R.id.tvRecyclerItemAbonoMontoValue);

        }

        public void bind(Abono abono, List<Cliente> listaClientes){
            tvRecyclerItemAbonoConceptoValue.setText(abono.getConceptoAbono());
            tvRecyclerItemAbonoFechaValue.setText(abono.getFechaFormateada());
            tvRecyclerItemAbonoMontoValue.setText(String.valueOf(abono.getMontoAbono()));
            boolean encontrado = false;
            for(Cliente cliente : listaClientes){
                for(Abono abonoCliente : cliente.getAbonosCliente()){
                    if(abonoCliente.getId() == abono.getId()){
                        tvRecyclerItemAbonoNombreValue.setText(cliente.getNombreCliente());
                        encontrado = true;
                        break;
                    }
                }
                if(encontrado){
                    break;
                }
            }
        }

    }

}
