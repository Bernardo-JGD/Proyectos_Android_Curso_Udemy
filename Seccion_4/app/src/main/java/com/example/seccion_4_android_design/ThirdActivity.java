package com.example.seccion_4_android_design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

        btnTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroTelefono = edTelefono.getText().toString();
                if(numeroTelefono != null){

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        //NOTA: En las versiones anteriores era "Manifest.permission.CALL_PHONE"
                        //Ahora tengo que anteponer "android.Manifest.permission.CALL_PHONE"
                        requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Estamos en el caso del telefono
        switch(requestCode){
            case PHONE_CALL_CODE:

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
                            return;
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