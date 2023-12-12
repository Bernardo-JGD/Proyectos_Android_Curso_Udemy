package com.example.seccion_7_ejercicio_cuentas.Models;

import java.math.BigDecimal;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Gasto extends RealmObject {

    @PrimaryKey
    private int idGasto;
    private String conceptoGasto;
    private String descripcionGasto;
    private Date fechaGasto;
    private double montoGasto;

    public Gasto(){

    }

    public Gasto(int idGasto, String conceptoGasto, String descripcionGasto, double montoGasto) {
        this.idGasto = idGasto;
        this.conceptoGasto = conceptoGasto;
        this.descripcionGasto = descripcionGasto;
        this.fechaGasto = new Date();
        this.montoGasto = montoGasto;
    }

    public int getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(int idGasto) {
        this.idGasto = idGasto;
    }

    public String getConceptoGasto() {
        return conceptoGasto;
    }

    public void setConceptoGasto(String conceptoGasto) {
        this.conceptoGasto = conceptoGasto;
    }

    public String getDescripcionGasto() {
        return descripcionGasto;
    }

    public void setDescripcionGasto(String descripcionGasto) {
        this.descripcionGasto = descripcionGasto;
    }

    public Date getFechaGasto() {
        return fechaGasto;
    }

    public void setFechaGasto(Date fechaGasto) {
        this.fechaGasto = fechaGasto;
    }

    public double getMontoGasto() {
        return montoGasto;
    }

    public void setMontoGasto(double montoGasto) {
        this.montoGasto = montoGasto;
    }
}
