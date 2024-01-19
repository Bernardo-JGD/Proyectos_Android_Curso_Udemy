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
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jbgd.seccion_7_ejercicio_cuentas_dga.Activitys.MainActivity;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Abono;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Gasto;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class AdapterRecyclerViewMainGastos extends RecyclerView.Adapter<AdapterRecyclerViewMainGastos.ViewHolder>{

    private List<Gasto> listaGastos;
    private Activity activity;
    private int layout;

    public AdapterRecyclerViewMainGastos(List<Gasto> listaGastos, int layout, Activity activity){
        this.listaGastos = listaGastos;
        this.activity = activity;
        this.layout = layout;
        sumarGastosMes();
    }

    private void ordenarListaAbonos(){
        if(listaGastos.size() > 1){
            for(int i = 0; i<listaGastos.size(); i++){
                for(int j = 0; j<listaGastos.size() - i - 1; j++){
                    if(listaGastos.get(j).getFechaGasto().after(listaGastos.get(j+1).getFechaGasto())){
                        listaGastos.add(j, listaGastos.get(j + 1));
                        listaGastos.remove(j+2);
                    }
                }
            }
        }
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
        holder.bind(listaGastos.get(position));
    }

    @Override
    public int getItemCount() {
        return listaGastos.size();
    }

    public void sumarGastosMes(){
        double sumaGastoMensual = 0;
        Calendar calendarActual = Calendar.getInstance();
        Date fechaActual = calendarActual.getTime();

        for(Gasto gasto : listaGastos){
            Date fechaGasto = gasto.getFechaGasto();

            Calendar calendarGasto = Calendar.getInstance();
            calendarGasto.setTime(fechaGasto);

            if(
                    calendarActual.get(Calendar.YEAR) == calendarGasto.get(Calendar.YEAR) &&
                            calendarActual.get(Calendar.MONTH) == calendarGasto.get(Calendar.MONTH)
            ){
                sumaGastoMensual += gasto.getMontoGasto();
            }

        }
        MainActivity.textViewGastosTotalMensual.setText("Gasto mensual: $ " + sumaGastoMensual);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{

        public TextView tvRecyclerItemGastoConceptoValue;
        public TextView tvRecyclerItemGastoFechaValue;
        public TextView tvRecyclerItemGastoMontoValue;

        private NumberPicker numberPickerFechaDia;
        private NumberPicker numberPickerFechaMes;
        private NumberPicker numberPickerFechaYear;
        private Date fechaSeleccionada;
        private TextView textViewFechaMostrada;

        private Realm realm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecyclerItemGastoConceptoValue = (TextView) itemView.findViewById(R.id.tvRecyclerItemGastoConceptoValue);
            tvRecyclerItemGastoFechaValue = (TextView) itemView.findViewById(R.id.tvRecyclerItemGastoFechaValue);
            tvRecyclerItemGastoMontoValue = (TextView) itemView.findViewById(R.id.tvRecyclerItemGastoMontoValue);

            Realm.init(activity);
            realm = Realm.getDefaultInstance();

            itemView.setOnCreateContextMenuListener(this);
        }

        public void bind(Gasto gasto){
            String montoGasto = String.valueOf(gasto.getMontoGasto());
            tvRecyclerItemGastoConceptoValue.setText(gasto.getConceptoGasto());
            tvRecyclerItemGastoFechaValue.setText(gasto.getFechaFormateada());
            tvRecyclerItemGastoMontoValue.setText("$ " + montoGasto);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(listaGastos.get(getAdapterPosition()).getConceptoGasto());
            MenuInflater menuInflater = activity.getMenuInflater();
            menuInflater.inflate(R.menu.menu_adapter_main_opciones, menu);

            for(int i = 0; i<menu.size(); i++){
                menu.getItem(i).setOnMenuItemClickListener(this);
            }
        }

        @Override
        public boolean onMenuItemClick(@NonNull MenuItem item) {

            Gasto gasto = listaGastos.get(this.getAdapterPosition());
            switch(item.getItemId()){
                case R.id.opcionEditar:
                    alertDialogEditarGasto(gasto);
                    return true;

                case R.id.opcionEliminar:
                    alertDialogEliminarGasto(gasto);
                    return true;

                default: return false;
            }

        }

        private void alertDialogEditarGasto(Gasto gasto){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Gasto: " + gasto.getConceptoGasto());
            builder.setMessage("Edite los campos que desee");

            View viewInflated = LayoutInflater.from(activity).inflate(R.layout.dialog_create_gasto, null);
            builder.setView(viewInflated);

            final EditText editTextConceptoGastoValor = viewInflated.findViewById(R.id.editTextConceptoGastoValor);
            final EditText editTextDescripcionGastoValor = viewInflated.findViewById(R.id.editTextDescripcionGastoValor);
            final EditText editTextMontoGastoValor = viewInflated.findViewById(R.id.editTextMontoGastoValor);
            editTextConceptoGastoValor.setText(gasto.getConceptoGasto());
            editTextDescripcionGastoValor.setText(gasto.getDescripcionGasto());
            editTextMontoGastoValor.setText(String.valueOf(gasto.getMontoGasto()));

            numberPickerFechaDia = viewInflated.findViewById(R.id.numberPickerFechaDia);
            numberPickerFechaMes = viewInflated.findViewById(R.id.numberPickerFechaMes);
            numberPickerFechaYear = viewInflated.findViewById(R.id.numberPickerFechaYear);
            textViewFechaMostrada = viewInflated.findViewById(R.id.textViewFechaMostrada);

            valoresPorDefectoFechaGasto();

            numberPickerFechaDia.setOnValueChangedListener(cambioDeFecha);
            numberPickerFechaMes.setOnValueChangedListener(cambioDeFecha);
            numberPickerFechaYear.setOnValueChangedListener(cambioDeFecha);



            builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String concepto = editTextConceptoGastoValor.getText().toString().trim();
                    String descripcion = editTextDescripcionGastoValor.getText().toString().trim();
                    //Aplicar al monto expresiones regulares cuando cree las validaciones
                    String monto = editTextMontoGastoValor.getText().toString().trim();
                    Date fecha = fechaSeleccionada;

                    if(true){//validaciones
                        realm.beginTransaction();
                        gasto.setConceptoGasto(concepto);
                        gasto.setDescripcionGasto(descripcion);
                        gasto.setMontoGasto(Double.parseDouble(monto));
                        gasto.setFechaGasto(fecha);
                        realm.insertOrUpdate(gasto);
                        realm.commitTransaction();
                        sumarGastosMes();
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

        private void alertDialogEliminarGasto(final Gasto gasto){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Eliminar gasto: " + gasto.getConceptoGasto());

            View viewInflated = LayoutInflater.from(activity).inflate(R.layout.dialog_delete_gasto, null);
            builder.setView(viewInflated);

            builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    realm.beginTransaction();
                    gasto.deleteFromRealm();
                    realm.commitTransaction();
                    sumarGastosMes();
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

        private void configurarNumberPickerDias(){
            int dia = numberPickerFechaDia.getValue();
            int mes = numberPickerFechaMes.getValue();
            int year = numberPickerFechaYear.getValue();

            if(mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12){
                numberPickerFechaDia.setMaxValue(31);
            } else if(mes == 4 || mes == 6 || mes == 9 || mes == 11){
                if(dia == 31){
                    numberPickerFechaDia.setValue(30);
                    numberPickerFechaDia.setMaxValue(30);
                }
            } else if(year%4 == 0){
                numberPickerFechaDia.setMaxValue(29);
            }else if(year%4 != 0 && dia == 29){
                numberPickerFechaDia.setMaxValue(28);
                numberPickerFechaDia.setValue(28);
            }
            dia = numberPickerFechaDia.getValue();

            configurarFechaMostrada(dia, mes, year);
        }

        private final NumberPicker.OnValueChangeListener cambioDeFecha = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                configurarNumberPickerDias();
            }
        };

        private void valoresPorDefectoFechaGasto(){
            int yearActual = Calendar.getInstance().get(Calendar.YEAR);

            numberPickerFechaDia.setMinValue(1);
            numberPickerFechaDia.setMaxValue(31);
            numberPickerFechaDia.setValue(1);

            numberPickerFechaMes.setMinValue(1);
            numberPickerFechaMes.setMaxValue(12);
            numberPickerFechaMes.setValue(1);

            numberPickerFechaYear.setMinValue(2000);
            numberPickerFechaYear.setMaxValue(yearActual);
            numberPickerFechaYear.setValue(yearActual);

            configurarFechaMostrada(numberPickerFechaDia.getValue(), numberPickerFechaMes.getValue(), numberPickerFechaYear.getValue());
        }

        private void configurarFechaMostrada(int dia, int mes, int year){
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, mes-1, dia);

            fechaSeleccionada = calendar.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd 'de' MMMM 'del' yyyy", new Locale("es", "ES"));
            String fechaFormateada = dateFormat.format(fechaSeleccionada);
            textViewFechaMostrada.setText("Fecha: " + fechaFormateada);
        }

    }

}
