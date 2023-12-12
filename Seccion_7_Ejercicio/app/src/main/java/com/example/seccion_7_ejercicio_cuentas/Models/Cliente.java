package com.example.seccion_7_ejercicio_cuentas.Models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Cliente extends RealmObject {

    @PrimaryKey
    private String nombreCliente;
    @Required
    private String nombreCortoCliente;
    @Required
    private String telefonoCliente;
    private String direccionCliente;
    private RealmList<Abono> listaAbonosCliente;

    public Cliente(){

    }

    public Cliente(String nombreCliente, String nombreCortoCliente, String telefonoCliente, String direccionCliente) {
        this.nombreCliente = nombreCliente;
        this.nombreCortoCliente = nombreCortoCliente;
        this.telefonoCliente = telefonoCliente;
        this.direccionCliente = direccionCliente;
        this.listaAbonosCliente = new RealmList<Abono>();
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

    public RealmList<Abono> getListaAbonosCliente() {
        return listaAbonosCliente;
    }

    public void setListaAbonosCliente(RealmList<Abono> listaAbonosCliente) {
        this.listaAbonosCliente = listaAbonosCliente;
    }
}
