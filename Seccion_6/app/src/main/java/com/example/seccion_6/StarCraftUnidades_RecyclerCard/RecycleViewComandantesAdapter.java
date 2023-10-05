package com.example.seccion_6.StarCraftUnidades_RecyclerCard;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleViewComandantesAdapter {

    private List<Comandante> listaComandantes;
    private int layout;
    private OnItemClickListener onItemClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewNombreComandante;
        public TextView textViewFraseComandante;
        public TextView textViewDescripcionComandante;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    public interface OnItemClickListener{
        void onItemClick();
    }

}
