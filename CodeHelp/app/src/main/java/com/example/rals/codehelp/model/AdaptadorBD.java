package com.example.rals.codehelp.model;

import android.provider.BaseColumns;

/**
 * Created by rals1_000 on 06/05/2015.
 */
public class AdaptadorBD {

    //Constantes BD

    //Usuarios
    public static abstract class UsersColumn implements BaseColumns{
        public static final String TABLE_NAME ="usuarios";
        public static final String IDUSUARIO = "id_usuario";
        public static final String NOMBRE = "nombre";
        public static final String APELLIDO = "apellido";
        public static final String EMAIL = "email";
        public static final String COMPANIA = "compania";
        public static final String LENGUAJES = "lenguajes";
        public static final String EXPERIENCIA = "experiencia";
    }

    //Solicitud
    public static abstract class SoliciColumns implements BaseColumns{
        public static final String IDSOLICITUD = "id_solicitud";
        public static final String IDCLIENTES = "usuario";
        public static final String IDEXPERTO = "experto";
        public static final String TITULO = "titulo";
        public static final String DESCRIPCION ="descripcion";
        public static final String CATEGORIA = "categoria";
        public static final String IDCHAT = "id_chat";
        public static final String DURACION = "duracion";
        public static final String ABIERTA = "abierta";
    }

    //Experto
    public static abstract class ExpertColumns implements BaseColumns{
        public static final String IDEXPERTO = "id_experto";
        public static final String NUMCASOS = "num_casos";
        public static final String COSTEMIN = "coste_min";
    }

    //Chat
    public static abstract class ChatColumns implements BaseColumns{
        public static final String IDCHAT = "id_chat";
        public static final String URLSTREAMING = "link_streaming";
    }

    //Mensajes
    public static abstract class MsgColumns implements BaseColumns{
        public static final String IDMENSAJE = "id_mensaje";
        public static final String IDCHAT = "id_chat";
        public static final String IDUSUARIO = "usuario";
        public static final String TEXTO = "texto";
        public static final String FECHA = "fecha";
    }


}
