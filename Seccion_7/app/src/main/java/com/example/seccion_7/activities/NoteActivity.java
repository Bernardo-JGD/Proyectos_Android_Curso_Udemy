package com.example.seccion_7.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.seccion_7.R;
import com.example.seccion_7.adapters.NoteAdapter;
import com.example.seccion_7.models.Board;
import com.example.seccion_7.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;

public class NoteActivity extends AppCompatActivity implements RealmChangeListener<Board> {

    private FloatingActionButton fabAddNote;
    private ListView listViewNote;

    private NoteAdapter adapter;
    private RealmList<Note> notes;
    private Realm realm;

    private int boardId;
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        realm = Realm.getDefaultInstance();

        if(getIntent().getExtras() != null){
            boardId = getIntent().getExtras().getInt("id");
        }

        board = realm.where(Board.class).equalTo("id", boardId).findFirst();
        board.addChangeListener(this);
        notes = board.getNotes();

        this.setTitle(board.getTitle());

        listViewNote = (ListView) findViewById(R.id.listViewNote);
        adapter = new NoteAdapter(this, notes, R.layout.list_view_note_item);
        listViewNote.setAdapter(adapter);

        fabAddNote = (FloatingActionButton) findViewById(R.id.fabAddNote);
        //Asi puedo cambiar el color de lo que contiene adentro el FloatingActionButton
        fabAddNote.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertForCreatingNote("Nuevo Note", "Ingrese una nota para " + board.getTitle());
            }
        });

        registerForContextMenu(listViewNote);

    }

    /* CRUD */
    private void createNewNote(String noteName) {
        realm.beginTransaction();
        Note note = new Note(noteName);
        realm.copyToRealm(note);
        board.getNotes().add(note);
        realm.commitTransaction();
    }

    private void editNote(String newDescription, Note note){
        realm.beginTransaction();
        note.setDescription(newDescription);
        realm.copyToRealmOrUpdate(note);
        realm.commitTransaction();
    }

    private void deleteNote(Note note){
        realm.beginTransaction();
        note.deleteFromRealm();
        realm.commitTransaction();
    }

    private void deleteAll(){
        realm.beginTransaction();
        board.getNotes().deleteAllFromRealm();
        realm.commitTransaction();
    }

    /* DIALOG */
    private void showAlertForCreatingNote(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(title != null){
            builder.setTitle(title);
        }
        if(message != null){
            builder.setMessage(message);
        }

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_note, null);
        builder.setView(viewInflated);

        final EditText input = (EditText) viewInflated.findViewById(R.id.editTextNewNote);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String noteName = input.getText().toString().trim();

                if(noteName.length() > 0){
                    createNewNote(noteName);
                }else{
                    Toast.makeText(getApplicationContext(), "Requiere una descripción", Toast.LENGTH_LONG).show();
                }

            }
        });

        builder.create();
        builder.show();
    }

    private void showAlertForEditingNote(String title, String message, final Note note){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(title != null){
            builder.setTitle(title);
        }
        if(message != null){
            builder.setMessage(message);
        }

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_note, null);
        builder.setView(viewInflated);

        final EditText input = (EditText) viewInflated.findViewById(R.id.editTextNewNote);

        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String noteName = input.getText().toString().trim();

                if(noteName.length() == 0){
                    Toast.makeText(getApplicationContext(), "Requiere un nombre", Toast.LENGTH_LONG).show();
                }else if(noteName.equals(note.getDescription())){
                    Toast.makeText(getApplicationContext(), "Escribió el mismo nombre", Toast.LENGTH_LONG).show();
                }else{
                    editNote(noteName, note);
                }

            }
        });

        builder.create();
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.delete_all_notes:
                deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_note_activity, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()){
            case R.id.edit_note:
                showAlertForEditingNote("", "", notes.get(info.position));
                return true;
            case R.id.delete_note:
                deleteNote(notes.get(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public void onChange(Board board) {
        adapter.notifyDataSetChanged();
    }
}