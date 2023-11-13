package com.example.seccion_7.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.seccion_7.R;
import com.example.seccion_7.models.Board;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class BoardAdapter extends BaseAdapter {

    private Context context;
    private List<Board> list;
    private int layout;

    public BoardAdapter(Context context, List<Board> list, int layout){
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Board getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View vista = convertView;

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        vista = layoutInflater.inflate(this.layout, null);

        String titulo = getItem(position).getTitle();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = dateFormat.format(getItem(position).getCreatedAt());

        String cantidadNotas = String.valueOf(getItem(position).getNotes().size());

        TextView textViewBoardTitle = vista.findViewById(R.id.textViewBoardTitle);
        TextView textViewBoardNotes = vista.findViewById(R.id.textViewBoardNotes);
        TextView textViewBoardDate = vista.findViewById(R.id.textViewBoardDate);

        textViewBoardTitle.setText("Titulo: " + titulo);
        textViewBoardDate.setText("Fecha: " + fecha);
        textViewBoardNotes.setText("Notas: " + cantidadNotas);

        return vista;
    }



}
