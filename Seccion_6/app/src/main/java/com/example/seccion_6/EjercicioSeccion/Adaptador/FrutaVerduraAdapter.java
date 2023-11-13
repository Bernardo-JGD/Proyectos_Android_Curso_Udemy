package com.example.seccion_6.EjercicioSeccion.Adaptador;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seccion_6.EjercicioSeccion.Activity.FrutaVerduraActivity;
import com.example.seccion_6.EjercicioSeccion.Modelo.FrutaVerdura;
import com.example.seccion_6.R;

import java.util.List;

import timber.log.Timber;

public class FrutaVerduraAdapter extends RecyclerView.Adapter<FrutaVerduraAdapter.ViewHolder>{

    private List<FrutaVerdura> listaFrutasVerduras;
    private int layout;
    private Activity activity;

    public FrutaVerduraAdapter(List<FrutaVerdura> listaFrutasVerduras, int layout, Activity activity){
        this.listaFrutasVerduras = listaFrutasVerduras;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(activity).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listaFrutasVerduras.get(position));
        final int pos = position;
        holder.ibIncremento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrutaVerdura fvSeleccionadaIncremento = listaFrutasVerduras.get(pos);
                if(FrutaVerduraActivity.listaFrutasVerdurasCompra != null){

                    if(FrutaVerduraActivity.listaFrutasVerdurasCompra.size() > 0){
                        for(FrutaVerdura fvCompra : FrutaVerduraActivity.listaFrutasVerdurasCompra){
                            if(fvCompra.getNombre() == fvSeleccionadaIncremento.getNombre()){
                                if(fvSeleccionadaIncremento.existe()){
                                    fvCompra.incrementarCantidad();
                                    listaFrutasVerduras.get(pos).restarCantidad();
                                    holder.tvCantidadTomadaValor.setText(String.valueOf(fvCompra.getCantidad()));
                                    holder.tvCantidadValor.setText(String.valueOf(listaFrutasVerduras.get(pos).getCantidad()));
                                    break;
                                }

                            }
                        }
                    }else{
                        if(fvSeleccionadaIncremento.getCantidad() > 0){
                            FrutaVerdura primerFV = new FrutaVerdura();
                            primerFV.setNombre(fvSeleccionadaIncremento.getNombre());
                            primerFV.setCategoria(fvSeleccionadaIncremento.getCategoria());
                            primerFV.setCantidad(1);
                            primerFV.setPrecio(fvSeleccionadaIncremento.getPrecio());
                            primerFV.setImagen(0);
                            FrutaVerduraActivity.listaFrutasVerdurasCompra.add(primerFV);
                            listaFrutasVerduras.get(pos).restarCantidad();
                            holder.tvCantidadTomadaValor.setText(String.valueOf(primerFV.getCantidad()));
                            holder.tvCantidadValor.setText(String.valueOf(listaFrutasVerduras.get(pos).getCantidad()));
                        }
                    }

                }


            }
        });

        holder.ibDecremento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Incremento", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaFrutasVerduras.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvNombreFrutaVerdura;
        public TextView tvPrecioValor;
        public TextView tvCantidadValor;
        public TextView tvCantidadTomadaValor;
        public ImageView ivFrutaVerdura;
        public ImageButton ibDecremento;
        public ImageButton ibIncremento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombreFrutaVerdura = (TextView) itemView.findViewById(R.id.tvNombreFrutaVerdura);
            tvPrecioValor = (TextView) itemView.findViewById(R.id.tvPrecioValor);
            tvCantidadValor = (TextView) itemView.findViewById(R.id.tvCantidadValor);
            tvCantidadTomadaValor = (TextView) itemView.findViewById(R.id.tvCantidadTomadaValor);
            ibDecremento = (ImageButton) itemView.findViewById(R.id.ibDecremento);
            ibIncremento = (ImageButton) itemView.findViewById(R.id.ibIncremento);
            ivFrutaVerdura = (ImageView) itemView.findViewById(R.id.ivFrutaVerdura);

        }

        public void bind(final FrutaVerdura frutaVerdura){
            tvNombreFrutaVerdura.setText(frutaVerdura.getNombre());
            tvPrecioValor.setText(String.valueOf(frutaVerdura.getPrecio()));
            tvCantidadValor.setText(String.valueOf(frutaVerdura.getCantidad()));
            ivFrutaVerdura.setImageResource(frutaVerdura.getImagen());

        }

    }
}
