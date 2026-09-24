package com.vk.tournamentapp.models;

import java.io.Serializable;

/**
 * Tournament summary. Phase 1A keeps the shape minimal - phase 1B extends it with
 * prize breakdown, room credentials and rules without breaking this contract.
 */
public class Tournament implements Serializable {

    public String id;
    public String title;
    public String mode;
    public String map;
    public double entryFee;
    public double prizePool;
    public double perKill;
    public int totalSlots;
    public int filledSlots;
    public long startTimeMillis;
    public String statusKey;
    public boolean live;

    public Tournament() {
    }

    public Tournament(String id, String title, String statusKey) {
        this.id = id;
        this.title = title;
        this.statusKey = statusKey;
    }

    public int getRemainingSlots() {
        return Math.max(0, totalSlots - filledSlots);
    }
}
