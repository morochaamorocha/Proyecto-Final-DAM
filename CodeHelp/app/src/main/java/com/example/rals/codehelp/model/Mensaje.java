package com.example.rals.codehelp.model;


import java.sql.Timestamp;


/**
 * Created by rals1_000 on 06/05/2015.
 */
public class Mensaje {

    private String idMensaje;
    private String idChat;
    private String idUsuario;
    private String texto;


    public Mensaje() {
    }

    public Mensaje(String idMensaje, String idChat, String idUsuario, String texto) {
        this.idMensaje = idMensaje;
        this.idChat = idChat;
        this.idUsuario = idUsuario;
        this.texto = texto;

    }

    public String getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

}
