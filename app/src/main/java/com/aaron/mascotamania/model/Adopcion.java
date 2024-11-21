package com.aaron.mascotamania.model;

public class Adopcion {

    String nombre;
    int edad;
    String especie;
    String estado;
    String imagenURL;

    public Adopcion() {
    }

    public Adopcion(String nombre, int edad, String especie, String estado, String imagenURL) {
        this.nombre = nombre;
        this.edad = edad;
        this.especie = especie;
        this.estado = estado;
        this.imagenURL = imagenURL;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }
}
