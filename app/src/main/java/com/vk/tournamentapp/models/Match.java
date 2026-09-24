package com.vk.tournamentapp.models;

import java.io.Serializable;

/** A tournament entry owned by the signed in player. */
public class Match implements Serializable {

    public String id;
    public String tournamentId;
    public String title;
    public long startTimeMillis;
    public String statusKey;
    public int rank;
    public int kills;
    public int points;
    public double prizeWon;

    public Match() {
    }

    public Match(String id, String title, String statusKey) {
        this.id = id;
        this.title = title;
        this.statusKey = statusKey;
    }
}
