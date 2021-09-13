package com.digitalware.comercios.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "fecha_pedido")
    private Date fechaPedido;

    @OneToMany(mappedBy = "pedido",  cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE, CascadeType.REMOVE})
    private List<PedidoLinea> lineasPedido;

    public Pedido() {
    }

    public Pedido(long id) {
        this.id = id;
    }

    public Pedido(String codigo, Date fechaPedido, List<PedidoLinea> lineasPedido) {
        this.codigo = codigo;
        this.fechaPedido = fechaPedido;
        this.lineasPedido = lineasPedido;
    }

    public boolean validarPedido(){
        if (!validarTamano()){
            return false;
        }else return validarLineas();

    }

    /**
     * Valida si existen lineas con productos repetidos o cantidad en cero
     * @return false si no es valida, de lo contrario tre
     */
    public boolean validarLineas(){
        Set<String> set = new HashSet<>();
        for (PedidoLinea linea:
                this.lineasPedido) {
            if (!linea.validarCantidad() || !set.add(linea.getProducto().getReferencia())){
                return false;
            }
        }
        return true;
    }

    public boolean validarTamano(){

        return (this.lineasPedido.size() >= 1);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<PedidoLinea> getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(List<PedidoLinea> lineasPedido) {
        this.lineasPedido = lineasPedido;
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

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", fechaPedido=" + fechaPedido +
                '}';
    }
}
