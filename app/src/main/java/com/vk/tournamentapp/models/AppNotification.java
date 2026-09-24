package com.vk.tournamentapp.models;

import java.io.Serializable;

/** In-app notification row. */
public class AppNotification implements Serializable {

    public String id;
    public String title;
    public String message;
    public String typeKey;
    public long timestampMillis;
    public boolean read;

    public AppNotification() {
    }

    public AppNotification(String id, String title, String message, String typeKey) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.typeKey = typeKey;
    }
}
