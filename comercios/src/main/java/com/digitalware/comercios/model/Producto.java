package com.digitalware.comercios.model;
import javax.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="referencia")
    private String referencia;
    @Column(name="descripcion")
    private String descripcion;


    public Producto(long id) {
        this.id = id;
    }

    public Producto(String referencia, String descripcion) {
        this.referencia = referencia;
        this.descripcion = descripcion;
    }

    public Producto() {

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
        return "Producto{" +
                "id=" + id +
                ", referencia='" + referencia + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

}

