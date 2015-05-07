package com.example.rals.codehelp.model;

import java.net.URL;
import java.util.UUID;

/**
 * Created by rals1_000 on 06/05/2015.
 */
public class Chat {

    private String idChat;
    private URL linkStreaming;

    public Chat() {
    }

    public Chat(String idChat) {
        this.idChat = idChat;
    }

    public Chat(String idChat, URL linkStreaming) {
        this.idChat = idChat;
        this.linkStreaming = linkStreaming;
    }

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public URL getLinkStreaming() {
        return linkStreaming;
    }

    public void setLinkStreaming(URL linkStreaming) {
        this.linkStreaming = linkStreaming;
    }
}
