package com.jbgd.seccion_7_ejercicio_cuentas_dga.Models;

import com.jbgd.seccion_7_ejercicio_cuentas_dga.App.MyApplication;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Cliente extends RealmObject implements Serializable {

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
        this.id = MyApplication.ClienteId.incrementAndGet();
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
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

    public Abono obtenerUltimoAbono(){
        if(abonosCliente.isEmpty()){
            return null;
        }
        Abono ultimoAbono = abonosCliente.get(0);
        if(abonosCliente.size() > 1){
            for(Abono abono : abonosCliente){
                if(abono.getFechaAbono().after(ultimoAbono.getFechaAbono())){
                    ultimoAbono = abono;
                }
            }
        }
        return ultimoAbono;
    }

    public void organizarAbonosPorFecha(){
        if(abonosCliente.size() > 1){
            for(int i = 0; i<abonosCliente.size(); i++){
                for(int j = 0; j<abonosCliente.size() - i - 1; j++){
                    if(abonosCliente.get(j).getFechaAbono().after(abonosCliente.get(j+1).getFechaAbono())){
                        Abono abonoTemporal = abonosCliente.get(j);
                        abonosCliente.set(j, abonosCliente.get(j+1));
                        abonosCliente.set(j+1, abonoTemporal);
                    }

                }
            }
        }
    }

}
