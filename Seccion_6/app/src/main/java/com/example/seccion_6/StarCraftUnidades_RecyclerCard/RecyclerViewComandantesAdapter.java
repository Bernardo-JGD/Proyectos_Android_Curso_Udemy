package com.example.seccion_6.StarCraftUnidades_RecyclerCard;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seccion_6.R;

import java.util.List;

public class RecyclerViewComandantesAdapter extends RecyclerView.Adapter<RecyclerViewComandantesAdapter.ViewHolder>{

    private List<Comandante> listaComandantes;
    private int layout;
    private OnItemClickListener clickListener;

    public RecyclerViewComandantesAdapter(List<Comandante> listaComandantes, int layout, OnItemClickListener clickListener){
        this.listaComandantes = listaComandantes;
        this.layout = layout;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerViewComandantesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listaComandantes.get(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewNombreComandante;
        public TextView textViewFraseComandante;
        public TextView textViewDescripcionComandante;
        public ImageView imageViewComandante;
        public CardView cardViewComandante;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewNombreComandante = itemView.findViewById(R.id.textViewNombreComandante);
            this.textViewFraseComandante = itemView.findViewById(R.id.textViewFraseComandante);
            this.textViewDescripcionComandante = itemView.findViewById(R.id.textViewDescripcionComandante);
            this.imageViewComandante = itemView.findViewById(R.id.imageViewComandante);
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

    }

    public interface OnItemClickListener{
        void onItemClick(Comandante comandante, int posicion);
    }

}
