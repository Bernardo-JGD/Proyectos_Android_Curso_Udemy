package com.example.seccion_6.EjercicioSeccion.Adaptador;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seccion_6.EjercicioSeccion.Modelo.FrutaVerdura;
import com.example.seccion_6.R;

import java.util.List;

public class TicketAdaptaer extends RecyclerView.Adapter<TicketAdaptaer.ViewHolder>{

    private List<FrutaVerdura> listaCompra;
    private int layout;
    private Activity activity;

    public TicketAdaptaer(List<FrutaVerdura> listaCompra, int layout, Activity activity) {
        this.listaCompra = listaCompra;
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
        holder.bind(listaCompra.get(position));
    }

    @Override
    public int getItemCount() {
        return listaCompra.size();
    }

    public int calcularTotal(){
        int costoTotal = 0;
        for(FrutaVerdura fv : listaCompra){
            costoTotal += (fv.getCantidadTomada() * fv.getPrecio());
        }
        return costoTotal;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvProductoValor;
        public TextView tvCantidadProductoValor;
        public TextView tvPrecioProductoValor;
        public TextView tvTotalProductoValor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductoValor = (TextView) itemView.findViewById(R.id.tvProductoValor);
            tvCantidadProductoValor = (TextView) itemView.findViewById(R.id.tvCantidadProductoValor);
            tvPrecioProductoValor = (TextView) itemView.findViewById(R.id.tvPrecioProductoValor);
            tvTotalProductoValor = (TextView) itemView.findViewById(R.id.tvTotalProductoValor);
        }

        public void bind(final FrutaVerdura frutaVerdura){
            tvProductoValor.setText(frutaVerdura.getNombre());
            tvCantidadProductoValor.setText(String.valueOf(frutaVerdura.getCantidadTomada()));
            tvPrecioProductoValor.setText(String.valueOf(frutaVerdura.getPrecio()));
            tvTotalProductoValor.setText(String.valueOf(frutaVerdura.totalPagar()));
        }

    }

}
