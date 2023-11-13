package com.example.seccion_6.EjercicioSeccion.Modelo;

import java.io.Serializable;

public class FrutaVerdura implements Serializable {

    private String nombre;
    private String categoria;
    private int precio;
    private int cantidad;
    private int imagen;
    //Este campo servirá para tener un valor para el item del recycler
    //quiza esto podría resolver la cuestión de no crear un lista de compra extra pues
    //esa lista de compra no usaría este atributo, si no el de cantidad, por lo que
    //quedarái obsoleto para ese fin
    private int cantidadTomada;

    public FrutaVerdura(){

    }

    public FrutaVerdura(String nombre, String categoria, int precio, int cantidad, int imagen) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getCantidadTomada() {
        return cantidadTomada;
    }

    public void setCantidadTomada(int cantidadTomada) {
        this.cantidadTomada = cantidadTomada;
    }

    public void restarCantidad(){
        this.cantidad = this.cantidad - 1;
    }

    public void restarCantidad(int cantidadRestar){
        this.cantidad = this.cantidad - cantidadRestar;
    }

    public void incrementarCantidad(){
        this.cantidad = this.cantidad + 1;
    }

    public boolean existe(){
        return getCantidad() > 0;
    }

}
