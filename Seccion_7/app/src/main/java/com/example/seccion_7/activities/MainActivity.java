package com.example.seccion_7.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.seccion_7.adapters.BoardAdapter;
import com.example.seccion_7.models.Board;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Board>>, AdapterView.OnItemClickListener{

    private Realm realm;
    private BoardAdapter adapter;
    private RealmResults<Board> boards;

    private ListView listView;
    private FloatingActionButton fabAddBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        boards = realm.where(Board.class).findAll();
        boards.addChangeListener(this);//this hace referencia al método sobres escrito por el implements RealmChangeListener

        adapter = new BoardAdapter(this, boards, R.layout.list_view_board_item);
        listView = (ListView) findViewById(R.id.listViewBoard);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);//Hace referencia al "onItemClick" implementado de AdapterView.OnItemClickListener

        //Boton agregar
        fabAddBoard = (FloatingActionButton) findViewById(R.id.fabAddBoard);
        //Asi puedo cambiar el color de lo que contiene adentro el FloatingActionButton
        fabAddBoard.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);
        fabAddBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertForCreatingBoard("Nuevo Board", "Ingrese un nombre");
            }
        });

        /*
        texto = (TextView) findViewById(R.id.texto);
        Spanned textoFormateado = Html.fromHtml(getString(R.string.cadena), Html.FROM_HTML_MODE_LEGACY) ;
        texto.setText(textoFormateado);
        */

        registerForContextMenu(listView);

    }

    /* CRUD */
    private void createNewBoard(String boardName) {
        realm.beginTransaction();
        Board board = new Board(boardName);
        realm.copyToRealm(board);
        realm.commitTransaction();
    }

    private void editBoard(String newName, Board board){
        realm.beginTransaction();
        board.setTitle(newName);
        realm.copyToRealmOrUpdate(board);
        realm.commitTransaction();
    }

    private void deleteBoard(Board board){
        realm.beginTransaction();
        board.deleteFromRealm();
        realm.commitTransaction();
    }

    private void deleteAll(){
        //Esto elimina toda la información registrada
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    /* DIALOG */
    private void showAlertForCreatingBoard(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(title != null){
            builder.setTitle(title);
        }
        if(message != null){
            builder.setMessage(message);
        }

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_board, null);
        builder.setView(viewInflated);

        final EditText input = (EditText) viewInflated.findViewById(R.id.editTextNewBoard);
        input.setHint("Board Name");

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String boardName = input.getText().toString().trim();

                if(boardName.length() > 0){
                    createNewBoard(boardName);
                }else{
                    Toast.makeText(getApplicationContext(), "Requiere un nombre", Toast.LENGTH_LONG).show();
                }

            }
        });

        builder.create();
        builder.show();
    }

    private void showAlertForEditingBoard(String title, String message, final Board board){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(title != null){
            builder.setTitle(title);
        }
        if(message != null){
            builder.setMessage(message);
        }

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_board, null);
        builder.setView(viewInflated);

        final EditText input = (EditText) viewInflated.findViewById(R.id.editTextNewBoard);
        input.setHint("Board Name");

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String boardName = input.getText().toString().trim();

                if(boardName.length() == 0){
                    Toast.makeText(getApplicationContext(), "Requiere un nombre", Toast.LENGTH_LONG).show();
                }else if(boardName.equals(board.getTitle())){
                    Toast.makeText(getApplicationContext(), "Escribió el mismo nombre", Toast.LENGTH_LONG).show();
                }else{
                    editBoard(boardName, board);
                }

            }
        });

        builder.create();
        builder.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_board_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.delete_all:
                deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(boards.get(info.position).getTitle());
        getMenuInflater().inflate(R.menu.context_menu_board_activity, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()){
            case R.id.delete_board:
                deleteBoard(boards.get(info.position));
                return true;
            case R.id.edit_board:
                showAlertForEditingBoard("Edit Board", "Cambiar el nombre del board", boards.get(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public void onChange(RealmResults<Board> boards) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        intent.putExtra("id", boards.get(position).getId());
        startActivity(intent);
    }
}