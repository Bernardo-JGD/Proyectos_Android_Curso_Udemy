package com.example.seccion_7.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.content.Context;
import android.widget.TextView;

import com.example.seccion_7.R;
import com.example.seccion_7.models.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private List<Note> list;
    private int layout;

    public NoteAdapter(Context context, List<Note> list, int layout){
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Note getItem(int position) {
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

        String descripcion = getItem(position).getDescription();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = dateFormat.format(getItem(position).getCreatedAt());

        TextView textViewNoteDescription = vista.findViewById(R.id.textViewNoteDescription);
        TextView textViewNoteCreatedAt = vista.findViewById(R.id.textViewNoteCreatedAt);

        textViewNoteDescription.setText(descripcion);
        textViewNoteCreatedAt.setText(fecha);

        return vista;
    }
}
