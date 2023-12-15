package com.jbgd.seccion_7_ejercicio_cuentas_dga.App;

import android.app.Application;

import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Abono;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Cliente;
import com.jbgd.seccion_7_ejercicio_cuentas_dga.Models.Gasto;

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

    private void setUpRealmConfig(){
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
    }

    //La T en <T> permite que el tipo sea de cualquiera de las clases modelo que cree
    //En results.max("id") solo uso id como referencia porque la clase me va a decir a que modelo me refiero
    //Si quisiera que cada clase tuviera un nombre de id diferente, tendría que crear casos o métodos para cada clase
    //Por eso usar "id" para todas me permita ahorrar codigo
    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size() > 0) ? new AtomicInteger(results.max("id").intValue()) : new AtomicInteger();
    }

}

/*

¡Entendido! Lo que estás haciendo aquí es crear una clase que extiende Application llamada
MyApplication. Al hacerlo, estás proporcionando una forma de inicializar y configurar cosas
que deben estar disponibles para toda tu aplicación desde el principio.

La razón por la que estás sobrescribiendo el método onCreate en esta clase es porque
onCreate en una clase que extiende Application se utiliza para realizar la inicialización
de la aplicación cuando se inicia. Este método se ejecuta antes de que cualquier componente
de la aplicación (actividades, servicios, etc.) sea creado.

En tu implementación de onCreate, estás haciendo varias cosas importantes:

Inicializas Realm: Llamas a Realm.init(getApplicationContext()); para inicializar Realm,
que es una base de datos local utilizada en aplicaciones de Android.

Configuras Realm: Llamas a setUpRealmConfig() para configurar la instancia predeterminada
de Realm con una configuración que indica que se debe eliminar la base de datos si se
detectan cambios en el esquema.

Obtienes y estableces IDs para tus modelos (Cliente, Abono, Gasto): Utilizas getIdByTable
para obtener y establecer IDs para tus modelos utilizando Realm.

En resumen, sobrescribir onCreate en tu clase MyApplication te permite realizar tareas de
inicialización cruciales para toda la aplicación antes de que cualquier otra cosa se
ejecute. Es un lugar conveniente para configurar y preparar recursos que deben estar
disponibles durante toda la vida de la aplicación.

* */
