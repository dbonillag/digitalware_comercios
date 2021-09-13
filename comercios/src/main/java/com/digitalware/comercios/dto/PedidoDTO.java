package com.digitalware.comercios.dto;
import com.digitalware.comercios.model.Pedido;
import com.digitalware.comercios.model.PedidoLinea;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidoDTO implements Serializable {

    private long id;

    private String codigo;

    private Date fechaPedido;

    private List<PedidoLineaDTO> lineasPedido;

    public PedidoDTO(){

    }

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.codigo = pedido.getCodigo();
        this.fechaPedido = pedido.getFechaPedido();
        this.lineasPedido = generearLineasPedidoDTO(pedido.getLineasPedido());
    }

    public List<PedidoLineaDTO> generearLineasPedidoDTO(List<PedidoLinea> lineasPedido){
        List<PedidoLineaDTO> lineasDTO = new ArrayList<PedidoLineaDTO>();
        lineasPedido.forEach((lineaPedido -> {lineasDTO.add(new PedidoLineaDTO(lineaPedido));}));
        return lineasDTO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public List<PedidoLineaDTO> getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(List<PedidoLineaDTO> lineasPedido) {
        this.lineasPedido = lineasPedido;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", fechaPedido=" + fechaPedido +
                ", lineasPedido=" + lineasPedido +
                '}';
    }
}

