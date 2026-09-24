package com.vk.tournamentapp.models;

import java.io.Serializable;

/** Signed in player. Phase 1A only ever builds mock instances. */
public class User implements Serializable {

    public String id;
    public String name;
    public String email;
    public String phone;
    public String ign;

    public User() {
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
