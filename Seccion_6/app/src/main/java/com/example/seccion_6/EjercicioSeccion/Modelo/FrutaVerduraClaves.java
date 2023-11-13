package com.example.seccion_6.EjercicioSeccion.Modelo;

public class FrutaVerduraClaves <Nombre, Categoria, Imagen>{

    private Nombre nombreFrutaVerdura;
    private Categoria categoria;
    private Imagen imagen;

    public FrutaVerduraClaves(Nombre nombreFrutaVerdura, Categoria categoria, Imagen imagen){
        this.nombreFrutaVerdura = nombreFrutaVerdura;
        this.categoria = categoria;
        this.imagen = imagen;
    }

    public Nombre getNombreFrutaVerdura() {
        return nombreFrutaVerdura;
    }

    public Categoria getCategoria(){
        return categoria;
    }

    public Imagen getImagen(){
        return imagen;
    }
}
