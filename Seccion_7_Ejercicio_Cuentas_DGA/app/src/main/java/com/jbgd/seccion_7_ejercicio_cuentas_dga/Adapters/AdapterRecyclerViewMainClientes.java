package com.jbgd.seccion_7_ejercicio_cuentas_dga.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Abono;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Cliente;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

public class AdapterRecyclerViewMainClientes extends RecyclerView.Adapter<AdapterRecyclerViewMainClientes.ViewHolder>{

    private List<Cliente> listaClientes;
    private int layout;
    private Activity activity;
    private OnItemClickListener clickListener;

    public AdapterRecyclerViewMainClientes(List<Cliente> listaClientes, int layout, Activity activity){
        this.listaClientes = listaClientes;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Cliente clienteSeleccionado = listaClientes.get(position);
        holder.bind(clienteSeleccionado);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null){
                    clickListener.OnItemClick(clienteSeleccionado);
                }
            }
        });

        holder.ivRecyclerItemLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !clienteSeleccionado.getTelefonoCliente().isEmpty() ){

                }else{
                    Toast.makeText(activity.getApplicationContext(), "No hay teléfono registrado", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }
    public void setOnItemClickListener(OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    public interface OnItemClickListener{
        void OnItemClick(Cliente cliente);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView tvRecyclerItemClienteNombreValue;
        public TextView tvRecyclerItemClienteFechaValue;
        public TextView tvRecyclerItemClienteMontoValue;
        public ImageView ivRecyclerItemLlamar;
        private Realm realm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecyclerItemClienteNombreValue = (TextView) itemView.findViewById(R.id.tvRecyclerItemClienteNombreValue);
            tvRecyclerItemClienteFechaValue = (TextView) itemView.findViewById(R.id.tvRecyclerItemClienteFechaValue);
            tvRecyclerItemClienteMontoValue = (TextView) itemView.findViewById(R.id.tvRecyclerItemClienteMontoValue);
            ivRecyclerItemLlamar = (ImageView) itemView.findViewById(R.id.ivRecyclerItemLlamar);

            Realm.init(activity.getApplicationContext());
            realm = Realm.getDefaultInstance();

            itemView.setOnCreateContextMenuListener(this);
        }

        public void bind(final Cliente cliente){
            String fecha = "";
            String monto = "No hay abonos";
            if(!cliente.getAbonosCliente().isEmpty()){
                fecha = cliente.obtenerUltimoAbono().getFechaFormateada();
                monto = String.valueOf(cliente.obtenerUltimoAbono().getMontoAbono());
                tvRecyclerItemClienteFechaValue.setText(fecha);
                tvRecyclerItemClienteMontoValue.setText(monto);
            }
            tvRecyclerItemClienteNombreValue.setText(cliente.getNombreCliente());
            tvRecyclerItemClienteFechaValue.setVisibility(View.GONE);
            tvRecyclerItemClienteMontoValue.setVisibility(View.GONE);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(listaClientes.get(this.getAdapterPosition()).getNombreCortoCliente());
            MenuInflater menuInflater = activity.getMenuInflater();
            menuInflater.inflate(R.menu.menu_adapter_main_opciones, menu);

            for(int i = 0; i<menu.size(); i++){
                menu.getItem(i).setOnMenuItemClickListener(this);
            }
        }

        @Override
        public boolean onMenuItemClick(@NonNull MenuItem item) {

            switch(item.getItemId()){
                case R.id.opcionEditar:
                    Cliente cliente = listaClientes.get(this.getAdapterPosition());
                    alertDialogEditarCliente(cliente);
                    return true;

                case R.id.opcionEliminar:

                    return true;

                default: return false;
            }

        }

        private void alertDialogEditarCliente(final Cliente cliente){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Cliente: " + cliente.getNombreCliente());
            builder.setMessage("Edite los campos que desee");

            View viewInflated = LayoutInflater.from(activity).inflate(R.layout.dialog_create_cliente, null);
            builder.setView(viewInflated);

            final EditText editTextNombreValor = viewInflated.findViewById(R.id.editTextNombreValor);
            final EditText editTextNombreCortoValor = viewInflated.findViewById(R.id.editTextNombreCortoValor);
            final EditText editTextTelefonoValor = viewInflated.findViewById(R.id.editTextTelefonoValor);
            final EditText editTextDireccionValor = viewInflated.findViewById(R.id.editTextDireccionValor);

            editTextNombreValor.setText(cliente.getNombreCliente());
            editTextNombreCortoValor.setText(cliente.getNombreCortoCliente());
            editTextTelefonoValor.setText(cliente.getTelefonoCliente());
            editTextDireccionValor.setText(cliente.getDireccionCliente());

            builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Aplicar al nombre expresiones regulares cuando cree las validaciones
                    //no debe admitir números ní símbolos raros, solo puntos y acentos
                    String nombre = editTextNombreValor.getText().toString().trim();
                    String nombreCorto = editTextNombreCortoValor.getText().toString().trim();
                    String telefono = editTextTelefonoValor.getText().toString().trim();
                    String direccion = editTextDireccionValor.getText().toString().trim();
                    if(true){//Aquí va una validación de los campos

                        realm.beginTransaction();
                        cliente.setNombreCliente(nombre);
                        cliente.setNombreCortoCliente(nombreCorto);
                        cliente.setTelefonoCliente(telefono);
                        cliente.setDireccionCliente(direccion);
                        realm.insertOrUpdate(cliente);
                        realm.commitTransaction();

                    }

                }
            });

            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.create();
            builder.show();

        }

        private void editarCliente(Cliente cliente){


            /*
            realm.beginTransaction();
            realm.insertOrUpdate(cliente);
            realm.commitTransaction();
            */
        }

    }

}
