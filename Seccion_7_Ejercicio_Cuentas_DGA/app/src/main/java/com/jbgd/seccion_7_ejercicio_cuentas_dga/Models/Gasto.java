package com.jbgd.seccion_7_ejercicio_cuentas_dga.Models;

import com.jbgd.seccion_7_ejercicio_cuentas_dga.App.MyApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Gasto extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String conceptoGasto;
    @Required
    private String descripcionGasto;
    @Required
    private Date fechaGasto;

    private double montoGasto;

    public Gasto(){
        this.id = MyApplication.GastoId.incrementAndGet();
    }

    public int getId() {
        return id;
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

    public String getFechaFormateada(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(fechaGasto);
    }

}
