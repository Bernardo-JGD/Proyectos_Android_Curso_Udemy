package com.jbgd.seccion_7_ejercicio_cuentas_dga.Models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Cliente extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String nombreCliente;
    @Required
    private String nombreCortoCliente;
    @Required
    private String telefonoCliente;
    private String direccionCliente;

    private RealmList<Abono> abonosCliente;

    public Cliente(){

    }

    public int getId() {
        return id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreCortoCliente() {
        return nombreCortoCliente;
    }

    public void setNombreCortoCliente(String nombreCortoCliente) {
        this.nombreCortoCliente = nombreCortoCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public RealmList<Abono> getAbonosCliente() {
        return abonosCliente;
    }

    public void setAbonosCliente(RealmList<Abono> abonosCliente) {
        this.abonosCliente = abonosCliente;
    }
}
