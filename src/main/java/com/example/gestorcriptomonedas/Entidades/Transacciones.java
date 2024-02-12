package com.example.gestorcriptomonedas.Entidades;

import com.example.gestorcriptomonedas.CONSTANTES.TipoOperacion;

import java.util.Date;

public class Transacciones {

    private int id;
    private int id_usuario;
    private int id_criptomoneda;
    private TipoOperacion tipoOperacion;
    private int cantidad;
    private double precio_unitario;
    private Date fecha;

    public Transacciones() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Transacciones(int id,  int id_usuario, int id_criptomoneda, TipoOperacion tipoOperacion, int cantidad, double precio_unitario, Date fecha) {
        this.id=id;
        this.id_usuario = id_usuario;
        this.id_criptomoneda = id_criptomoneda;
        this.tipoOperacion = tipoOperacion;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.fecha = fecha;
    }
    public Transacciones(int id_usuario, int id_criptomoneda, TipoOperacion tipoOperacion, int cantidad, double precio_unitario, Date fecha) {
        this.id_usuario = id_usuario;
        this.id_criptomoneda = id_criptomoneda;
        this.tipoOperacion = tipoOperacion;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.fecha = fecha;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_criptomoneda() {
        return id_criptomoneda;
    }

    public void setId_criptomoneda(int id_criptomoneda) {
        this.id_criptomoneda = id_criptomoneda;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }



}
