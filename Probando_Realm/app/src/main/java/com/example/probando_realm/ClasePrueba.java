package com.example.probando_realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ClasePrueba extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String nombre;

}
