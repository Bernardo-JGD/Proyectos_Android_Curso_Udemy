package com.example.seccion_6.StarCraftUnidades_RecyclerCard;

import android.app.Activity;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seccion_6.R;

import java.util.List;

public class RecyclerViewComandantesAdapter extends RecyclerView.Adapter<RecyclerViewComandantesAdapter.ViewHolder>{

    private List<Comandante> listaComandantes;
    private int layout;
    private OnItemClickListener clickListener;
    private Activity activity;

    // Pasamos el activity en vez del context, ya que nos hará falta para poder inflar en context menu
    public RecyclerViewComandantesAdapter(List<Comandante> listaComandantes, int layout, Activity activity, OnItemClickListener clickListener){
        this.listaComandantes = listaComandantes;
        this.layout = layout;
        this.clickListener = clickListener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerViewComandantesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(activity).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listaComandantes.get(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return listaComandantes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
        public TextView textViewNombreComandante;
        public TextView textViewFraseComandante;
        public TextView textViewDescripcionComandante;
        public TextView textViewMostrarMasMenos;
        public ImageView imageViewComandante;
        public CardView cardViewComandante;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewNombreComandante = itemView.findViewById(R.id.textViewNombreComandante);
            this.textViewFraseComandante = itemView.findViewById(R.id.textViewFraseComandante);
            this.textViewDescripcionComandante = itemView.findViewById(R.id.textViewDescripcionComandante);
            this.imageViewComandante = itemView.findViewById(R.id.imageViewComandante);
            this.textViewMostrarMasMenos = itemView.findViewById(R.id.textViewMostrarMasMenos);
            //this.cardViewComandante = itemView.findViewById(R.id.cardViewComandante);

            // Añadimos al view el listener para el context menu, en vez de hacerlo en
            // el activity mediante el método registerForContextMenu
            itemView.setOnCreateContextMenuListener(this);

        }

        public void bind(final Comandante comandante, final OnItemClickListener clickListener){
            this.textViewNombreComandante.setText(comandante.getNombre());
            this.textViewFraseComandante.setText(comandante.getFrase());
            this.textViewDescripcionComandante.setText(comandante.getDescripcion());
            this.imageViewComandante.setImageResource(comandante.getImagen());
            //#D69A00 protos
            //#6B288F zerg
            //#0833B5 terran

            switch(comandante.getFaccion()){
                case "Protos":
                    this.textViewNombreComandante.setBackgroundColor(Color.parseColor("#D69A00"));
                    break;

                case "Terran":
                    this.textViewNombreComandante.setBackgroundColor(Color.parseColor("#0833B5"));
                    break;

                case "Zerg":
                    this.textViewNombreComandante.setBackgroundColor(Color.parseColor("#6B288F"));
                    break;

                default:
                    this.cardViewComandante.setCardBackgroundColor(Color.parseColor("#FFFFFF"));


            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(comandante, getAdapterPosition());
                }
            });

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            // Recogemos la posición con el método getAdapterPosition
            Comandante comandante = listaComandantes.get(this.getAdapterPosition());
            // Establecemos título e icono para cada elemento, mirando en sus propiedades
            menu.setHeaderTitle(comandante.getNombre());
            menu.setHeaderIcon(comandante.getImagen());
            // Inflamos el menu
            MenuInflater inflater = activity.getMenuInflater();
            inflater.inflate(R.menu.menu_comandantes, menu);

            // Por último, añadimos uno por uno, el listener onMenuItemClick para
            // controlar las acciones en el contextMenu, anteriormente lo manejábamos
            // con el método onContextItemSelected en el activity
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setOnMenuItemClickListener(this);
            }
        }

        // Sobreescribimos onMenuItemClick, dentro del ViewHolder,
        // en vez de hacerlo en el activity bajo el nombre onContextItemSelected
        @Override
        public boolean onMenuItemClick(@NonNull MenuItem item) {
            // No obtenemos nuestro objeto info
            // porque la posición la podemos rescatar desde getAdapterPosition

            switch(item.getItemId()){
                case R.id.opcionEliminarComandante:
                    listaComandantes.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    return true;

                default: return false;
            }
        }

    }

    public void limpiarListaComandantes(){
        this.listaComandantes.clear();
        notifyDataSetChanged();
    }

    public void actualizarComandantes(List<Comandante> comandantesNuevos){
        this.listaComandantes = comandantesNuevos;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(Comandante comandante, int posicion);
    }

}
