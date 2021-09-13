package com.digitalware.comercios.dto;

import com.digitalware.comercios.model.PedidoLinea;
import com.digitalware.comercios.model.Producto;

import java.io.Serializable;

public class PedidoLineaDTO implements Serializable {


    private long id;

    private String referenciaProducto;

    private String descripcionProducto;

    private Integer cantidad;

    public PedidoLineaDTO(PedidoLinea pedidoLinea) {
        this.id = pedidoLinea.getId();
        this.referenciaProducto = pedidoLinea.getProducto().getReferencia();
        this.descripcionProducto = pedidoLinea.getProducto().getDescripcion();
        this.cantidad = pedidoLinea.getCantidad();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReferenciaProducto() {
        return referenciaProducto;
    }

    public void setReferenciaProducto(String referenciaProducto) {
        this.referenciaProducto = referenciaProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "PedidoLineaDTO{" +
                "id=" + id +
                ", referenciaProducto='" + referenciaProducto + '\'' +
                ", descripcionProducto='" + descripcionProducto + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}
