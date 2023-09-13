package com.example.seccion_4_android_design.Clases;

import java.sql.Date;

public class Compra {
    private int cantidadFruta;
    private int precioFruta;
    private String metodoPago;
    private int cantidadPago;
    private Date fechaCompra;

    public int getCantidadFruta() {
        return cantidadFruta;
    }

    public void setCantidadFruta(int cantidadFruta) {
        this.cantidadFruta = cantidadFruta;
    }

    public int getPrecioFruta() {
        return precioFruta;
    }

    public void setPrecioFruta(int precioFruta) {
        this.precioFruta = precioFruta;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public int getCantidadPago() {
        return cantidadPago;
    }

    public void setCantidadPago(int cantidadPago) {
        this.cantidadPago = cantidadPago;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

}
