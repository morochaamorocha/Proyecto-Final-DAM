package com.example.rals.codehelp.model;

/**
 * Created by rals1_000 on 05/05/2015.
 */
public class Usuario {

    private String userID;
    private String nombre;
    private String apellido;
    private String email;
    private String compania;
    private String lenguajes;
    private String experiencia;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String email, String compania, String lenguajes, String experiencia) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.compania = compania;
        this.lenguajes = lenguajes;
        this.experiencia = experiencia;

    }

    public Usuario(String userID, String nombre, String apellido, String email, String compania, String lenguajes, String experiencia) {
        this.userID = userID;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.compania = compania;
        this.lenguajes = lenguajes;
        this.experiencia = experiencia;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public String getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(String lenguajes) {
        this.lenguajes = lenguajes;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }
}
