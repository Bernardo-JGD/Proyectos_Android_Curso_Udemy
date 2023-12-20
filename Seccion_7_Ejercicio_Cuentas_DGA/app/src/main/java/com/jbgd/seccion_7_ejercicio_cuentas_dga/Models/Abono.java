package com.jbgd.seccion_7_ejercicio_cuentas_dga.Models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Abono extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String conceptoAbono;
    private String descripcionAbono;
    @Required
    private Date fechaAbono;

    private double montoAbono;

    public Abono(){

    }

    public int getId() {
        return id;
    }

    public String getConceptoAbono() {
        return conceptoAbono;
    }

    public void setConceptoAbono(String conceptoAbono) {
        this.conceptoAbono = conceptoAbono;
    }

    public String getDescripcionAbono() {
        return descripcionAbono;
    }

    public void setDescripcionAbono(String descripcionAbono) {
        this.descripcionAbono = descripcionAbono;
    }

    public Date getFechaAbono() {
        return fechaAbono;
    }

    public void setFechaAbono(Date fechaAbono) {
        this.fechaAbono = fechaAbono;
    }

    public double getMontoAbono() {
        return montoAbono;
    }

    public void setMontoAbono(double montoAbono) {
        this.montoAbono = montoAbono;
    }

    public String getFechaFormateada(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(fechaAbono);
    }

}
