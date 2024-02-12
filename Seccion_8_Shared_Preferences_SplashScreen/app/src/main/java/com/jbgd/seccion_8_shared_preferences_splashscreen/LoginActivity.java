package com.jbgd.seccion_8_shared_preferences_splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.jbgd.seccion_8_shared_preferences_splashscreen.Utils.Util;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    private EditText edCorreo;
    private EditText edPassword;
    private Switch switchRecordar;
    private Button btnIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uiBind();

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        setCredentialsIfExist();

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edCorreo.getText().toString();
                String password = edPassword.getText().toString();

                if(login(email, password)){
                    goToMain();
                    saveOnPreferences(email, password);
                }
            }
        });
    }

    private void uiBind(){
        edCorreo = (EditText) findViewById(R.id.edCorreo);
        edPassword = (EditText) findViewById(R.id.edPassword);
        switchRecordar = (Switch) findViewById(R.id.switchRecordar);
        btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
    }

    private void setCredentialsIfExist(){
        String email = Util.getUserMailPreferences(preferences);
        String password = Util.getUserPasswordPreferences(preferences);
        if(!TextUtils.isEmpty(email )&& !TextUtils.isEmpty(password)){
            edCorreo.setText(email);
            edPassword.setText(password);
        }
    }

    private boolean login(String email, String password){
        if(!isValidEmail(email)){
            Toast.makeText(this, "Correo no valido", Toast.LENGTH_LONG).show();
            return false;
        }else if(!isValidPassword(password)){
            Toast.makeText(this, "Contraseña muy corta", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void saveOnPreferences(String email, String password){
        if(switchRecordar.isChecked()){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email", email);
            editor.putString("password", password);
            editor.commit();
            editor.apply();
        }
        /*
        apply() cambia el objeto SharedPreferences en la memoria de inmediato, pero escribe las actualizaciones en el disco
        de forma asíncrona. Como alternativa, puedes usar commit() para escribir los datos en el disco de forma síncrona.
        Sin embargo, debido a que commit() es síncrono, debes evitar llamarlo desde tu subproceso principal, ya que podría
        pausar el procesamiento de la IU.
        * */
    }

    private boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password){
        return password.length() > 4;
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        //Evita que regrese a esta pantalla una vez que vaya a la siguiente
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }



}