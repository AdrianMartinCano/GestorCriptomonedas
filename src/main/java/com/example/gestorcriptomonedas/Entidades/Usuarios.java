package com.example.gestorcriptomonedas.Entidades;

public class Usuarios {


    /*CREATE TABLE usuarios (
    id_usuario INTEGER PRIMARY KEY,
    nombre TEXT,
    usuario TEXT UNIQUE,  -- Campo para el nombre de usuario (debe ser único)
    contraseña TEXT,       -- Campo para la contraseña
    saldo REAL
);*/

    private int id_usuario;
    private String nombre;
    private String usuario;
    private String contraseña;
    private double Saldo;

    public Usuarios(int id_usuario, String nombre, String usuario, String contraseña, double saldo) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.usuario = usuario;
        this.contraseña = contraseña;
        Saldo = saldo;
    }

    public Usuarios(String nombre, String usuario, String contraseña, double saldo) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.contraseña = contraseña;
        Saldo = saldo;
    }

    public Usuarios() {
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public double getSaldo() {
        return Saldo;
    }

    public void setSaldo(double saldo) {
        Saldo = saldo;
    }
}
