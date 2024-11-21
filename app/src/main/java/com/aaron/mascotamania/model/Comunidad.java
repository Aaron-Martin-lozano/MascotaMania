package com.aaron.mascotamania.model;

public class Comunidad {

    String usuario;
    String pregunta;

    public Comunidad() {
    }

    public Comunidad(String usuario, String pregunta) {
        this.usuario = usuario;
        this.pregunta = pregunta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
}
