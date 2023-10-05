package com.example.seccion_6.StarCraftUnidades_RecyclerCard;

public class Comandante {

    private String nombre;
    private String faccion;
    private String frase;
    private String descripcion;
    private int imagen;

    public Comandante(String nombre, String faccion, String frase, String descripcion, int imagen){
        this.nombre = nombre;
        this.faccion = faccion;
        this.frase = frase;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFaccion() {
        return faccion;
    }

    public void setFaccion(String faccion) {
        this.faccion = faccion;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
