package com.example.seccion_6.EjercicioSeccion.Adaptador;

import android.app.Activity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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
                if(listaFrutasVerduras.get(pos).existe()){
                    listaFrutasVerduras.get(pos).restarCantidad();
                    listaFrutasVerduras.get(pos).incrementarCantidadTomada();
                    holder.tvCantidadValor.setText(String.valueOf(listaFrutasVerduras.get(pos).getCantidad()));
                    holder.tvCantidadTomadaValor.setText(String.valueOf(listaFrutasVerduras.get(pos).getCantidadTomada()));
                    calcularPrecio();
                    calcularCantidades();
                }
            }
        });

        holder.ibDecremento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listaFrutasVerduras.get(pos).existeCantidadTomada()){
                    listaFrutasVerduras.get(pos).incrementarCantidad();
                    listaFrutasVerduras.get(pos).restarCantidadTomada();
                    holder.tvCantidadValor.setText(String.valueOf(listaFrutasVerduras.get(pos).getCantidad()));
                    holder.tvCantidadTomadaValor.setText(String.valueOf(listaFrutasVerduras.get(pos).getCantidadTomada()));
                    calcularPrecio();
                    calcularCantidades();
                }
            }
        });

    }

    private void calcularPrecio(){
        int costoTotal = 0;
        for(FrutaVerdura fv : listaFrutasVerduras){
            costoTotal += (fv.getCantidadTomada() * fv.getPrecio());
        }
        FrutaVerduraActivity.tvTotalPrecioValor.setText(String.valueOf(costoTotal));
    }

    private void calcularCantidades(){
        int cantidadTotal = 0;
        for(FrutaVerdura fv : listaFrutasVerduras){
            cantidadTotal += fv.getCantidadTomada() ;
        }
        FrutaVerduraActivity.tvCantidadTotalValor.setText(String.valueOf(cantidadTotal));
    }

    public List<FrutaVerdura> getListaFrutasVerduras() {
        return listaFrutasVerduras;
    }

    @Override
    public int getItemCount() {
        return listaFrutasVerduras.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{

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

            itemView.setOnCreateContextMenuListener(this);

        }

        public void bind(final FrutaVerdura frutaVerdura){
            tvNombreFrutaVerdura.setText(frutaVerdura.getNombre());
            tvPrecioValor.setText(String.valueOf(frutaVerdura.getPrecio()));
            tvCantidadValor.setText(String.valueOf(frutaVerdura.getCantidad()));
            ivFrutaVerdura.setImageResource(frutaVerdura.getImagen());
            tvCantidadTomadaValor.setText(String.valueOf(frutaVerdura.getCantidadTomada()));
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.setHeaderTitle(listaFrutasVerduras.get(this.getAdapterPosition()).getNombre());
            MenuInflater inflater = activity.getMenuInflater();
            inflater.inflate(R.menu.menu_fv, menu);

            // Por último, añadimos uno por uno, el listener onMenuItemClick para
            // controlar las acciones en el contextMenu, anteriormente lo manejábamos
            // con el método onContextItemSelected en el activity
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setOnMenuItemClickListener(this);
            }

        }

        @Override
        public boolean onMenuItemClick(@NonNull MenuItem item) {
            // No obtenemos nuestro objeto info
            // porque la posición la podemos rescatar desde getAdapterPosition

            switch(item.getItemId()){
                case R.id.opcionEliminarFV:
                    listaFrutasVerduras.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    calcularPrecio();
                    calcularCantidades();
                    return true;

                default: return false;
            }
        }

    }
}
