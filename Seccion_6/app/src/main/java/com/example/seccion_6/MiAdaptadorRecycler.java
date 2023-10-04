package com.example.seccion_6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MiAdaptadorRecycler extends RecyclerView.Adapter<MiAdaptadorRecycler.ViewHolder>{

    private List<String> names;
    private int layout;
    private OnItemClickListener itemClickListener;

    public MiAdaptadorRecycler(List<String> names, int layout, OnItemClickListener itemClickListener){
        this.names = names;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MiAdaptadorRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MiAdaptadorRecycler.ViewHolder holder, int position) {
        holder.bind(names.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return this.names.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        }

        public void bind(final String name, final OnItemClickListener listener){

            this.textViewName.setText(name);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    listener.onItemClick(name, getAdapterPosition());
                }
            });

        }

    }

    public interface OnItemClickListener{
        void onItemClick(String name, int position);
    }

}
