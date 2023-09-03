package com.example.seccion_4_android_design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private EditText edTelefono;
    private EditText edWeb;
    private ImageButton btnTelefono;
    private ImageButton btnWeb;
    private ImageButton btnCamara;

    private final int PHONE_CALL_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        edTelefono = (EditText) findViewById(R.id.edTelefono);
        edWeb = (EditText) findViewById(R.id.edWeb);
        btnTelefono = (ImageButton) findViewById(R.id.btnTelefono);
        btnWeb = (ImageButton) findViewById(R.id.btnWeb);
        btnCamara = (ImageButton) findViewById(R.id.btnCamara);

        //Boton para llamar
        btnTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroTelefono = edTelefono.getText().toString();
                if(numeroTelefono != null && !numeroTelefono.isEmpty()){

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                        //Comprobar si ha aceptado, no ha aceptado o nunca se la preguntado
                        if(ChecarPermiso(android.Manifest.permission.CALL_PHONE)){

                            //Ha aceptado
                            Intent intentLlamar = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numeroTelefono));

                            if(ActivityCompat.checkSelfPermission(ThirdActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                                return;//Si no entra aquí, continua con el flujo
                            }

                            startActivity(intentLlamar);
                        }else{

                            //Ha denegado o es la primera vez que se le pregunta
                            if(!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){

                                //No se le ha preguntado aun
                                //NOTA: En las versiones anteriores era "Manifest.permission.CALL_PHONE"
                                //Ahora tengo que anteponer "android.Manifest.permission.CALL_PHONE"
                                requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);

                            }else{

                                //Ha denegado
                                Toast.makeText(ThirdActivity.this, "Por favor, habilite el permiso para llamar", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                i.addCategory(Intent.CATEGORY_DEFAULT);
                                i.setData(Uri.parse("package:" + getPackageName())); //Paquete de esta aplicación
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(i);

                            }
                        }
                    }else{
                        OldVersions(numeroTelefono);
                    }

                }
            }

            private void OldVersions(String numeroTelefono){
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + numeroTelefono));
                if(ChecarPermiso(android.Manifest.permission.CALL_PHONE)){
                    startActivity(intentCall);
                }else{
                    Toast.makeText(ThirdActivity.this, "Permiso denegado", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Boton para ir al navegador
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String direccionWeb = edWeb.getText().toString();
                if(direccionWeb != null && !direccionWeb.isEmpty()){
                    Intent intentWeb = new Intent();
                    //Una forma diferente de agregar los parametros
                    intentWeb.setAction(Intent.ACTION_VIEW);
                    intentWeb.setData(Uri.parse("http://" + direccionWeb));

                    startActivity(intentWeb);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Estamos en el caso del telefono
        switch(requestCode){
            case PHONE_CALL_CODE:

                //Como solo estámos solicitando un permiso, apuntamos a la posicion 0
                String permission = permissions[0];
                int result = grantResults[0];

                if(permission.equals(android.Manifest.permission.CALL_PHONE)){

                    //Comprobar si ha sido aceptado o denegada la peticion de permiso
                    if(result == PackageManager.PERMISSION_GRANTED){
                        //Concedio permiso
                        String numeroTelefono = edTelefono.getText().toString();
                        //El "tel:" tiene que llevar los ":" obligatoriamente para que funcione bien
                        Intent intentLlamar = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numeroTelefono));
                        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                            return;//Si no entra aquí, continua con el flujo
                        }
                        startActivity(intentLlamar);
                    }else{
                        //No concedio el permiso
                        Toast.makeText(ThirdActivity.this, "Permiso denegado", Toast.LENGTH_LONG).show();
                    }
                }

                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }


    }

    private boolean ChecarPermiso(String permission){
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}