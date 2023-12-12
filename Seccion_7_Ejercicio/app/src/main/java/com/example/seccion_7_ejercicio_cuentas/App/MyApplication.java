package com.example.seccion_7_ejercicio_cuentas.App;

import android.app.Application;

import com.example.seccion_7_ejercicio_cuentas.Models.Abono;
import com.example.seccion_7_ejercicio_cuentas.Models.Cliente;
import com.example.seccion_7_ejercicio_cuentas.Models.Gasto;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MyApplication extends Application {

    public static AtomicInteger ClienteId = new AtomicInteger();
    public static AtomicInteger AbonoId = new AtomicInteger();
    public static AtomicInteger GastoId = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());

        setUpRealmConfig();

        Realm realm = Realm.getDefaultInstance();
        ClienteId = getIdByTable(realm, Cliente.class);
        AbonoId = getIdByTable(realm, Abono.class);
        GastoId = getIdByTable(realm, Gasto.class);

        realm.close();

    }

    private void setUpRealmConfig() {
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size() > 0) ? new AtomicInteger(results.max("id").intValue()) : new AtomicInteger();
    }


}
