package com.example.seccion_5_list_grid_view.ProyectoFrutas.Modelo;

public class FrutaVerdura {

    private String nombreFrutaVerdura;
    private String categoria;
    private int icono;

    public FrutaVerdura(){

    }

    public FrutaVerdura(String nombreFrutaVerdura, String categoria, int icono) {
        this.nombreFrutaVerdura = nombreFrutaVerdura;
        this.categoria = categoria;
        this.icono = icono;
    }

    public String getNombreFrutaVerdura() {
        return nombreFrutaVerdura;
    }

    public void setNombreFrutaVerdura(String nombreFrutaVerdura) {
        this.nombreFrutaVerdura = nombreFrutaVerdura;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }
}
