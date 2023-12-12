package com.example.seccion_7_ejercicio_cuentas.Models;

import java.math.BigDecimal;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Abono extends RealmObject {

    @PrimaryKey
    private int idAbono;
    @Required
    private String conceptoAbono;
    private String descripcionAbono;
    @Required
    private Date fechaAbono;
    private double montoAbono;

    public Abono(){

    }

    public Abono(int idAbono, String conceptoAbono, String descripcionAbono, double montoAbono) {
        this.idAbono = idAbono;
        this.conceptoAbono = conceptoAbono;
        this.descripcionAbono = descripcionAbono;
        this.fechaAbono = new Date();
        this.montoAbono = montoAbono;
    }

    public int getIdAbono() {
        return idAbono;
    }

    public void setIdAbono(int idAbono) {
        this.idAbono = idAbono;
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
}
