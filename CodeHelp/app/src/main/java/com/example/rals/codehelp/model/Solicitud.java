package com.example.rals.codehelp.model;


import java.util.UUID;

/**
 * Created by rals1_000 on 06/05/2015.
 */
public class Solicitud {

    private int idSolicitud;
    private String idCliente;
    private String idExperto;
    private String titulo;
    private String descripcion;
    private String categoria;
    private String idChat;
    private int duracion;
    private boolean abierta;

    public Solicitud(int idSolicitud, Usuario user, Experto experto, String titulo, String descripcion, String categoria, String idChat, int duracion, boolean abierta) {
        this.idSolicitud = idSolicitud;
        this.idCliente = user.getUserID();
        this.idExperto = experto.getIdExperto();
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.idChat = idChat;
        this.duracion = duracion;
        this.abierta = abierta;
    }

    public Solicitud() {

    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdExperto() {
        return idExperto;
    }

    public void setIdExperto(String idExperto) {
        this.idExperto = idExperto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }
}
