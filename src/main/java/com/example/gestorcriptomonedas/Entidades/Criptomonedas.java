package com.example.gestorcriptomonedas.Entidades;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Criptomonedas {

    private String nombre;
    private double precio;
    private byte[] imagen;

    public Criptomonedas(String nombre, double precio, byte[] imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
    }
    public Criptomonedas(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;

    }

    public Criptomonedas() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] rutaImagen) {
        this.imagen = rutaImagen;
    }

    public Bitmap obtenerBitmapDesdeBytes() {
        if (imagen != null) {
            return BitmapFactory.decodeByteArray(imagen, 0, imagen.length);
        }
        return null;
    }

    private int obtenerIdRecursoPorNombre(Context context, String nombre) {
        // Obtén el identificador del recurso por el nombre en la carpeta res/drawable
        return context.getResources().getIdentifier(nombre, "drawable", context.getPackageName());
    }

    public int getImagenId(Context context) {
        // Utiliza el nombre de la criptomoneda para obtener el identificador del recurso
        return obtenerIdRecursoPorNombre(context, nombre.toLowerCase()); // Asegúrate de que los nombres en res/drawable son en minúsculas
    }
}
