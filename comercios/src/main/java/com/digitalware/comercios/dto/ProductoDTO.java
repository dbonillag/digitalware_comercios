package com.digitalware.comercios.dto;


import com.digitalware.comercios.model.Producto;

import java.io.Serializable;
import java.util.Date;


public class ProductoDTO implements Serializable {

    private long id;

    private String referencia;

    private String descripcion;

    public ProductoDTO(Producto producto) {
        this.id = producto.getId();
        this.referencia = producto.getReferencia();
        this.descripcion = producto.getDescripcion();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
                "id=" + id +
                ", referencia='" + referencia + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
