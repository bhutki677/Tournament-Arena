package com.vk.tournamentapp.models;

import java.io.Serializable;

/** Wallet ledger row. statusKey maps to ta_status_* colours and status_* labels. */
public class WalletTransaction implements Serializable {

    public String id;
    public String title;
    public double amount;
    public String statusKey;
    public long timestampMillis;
    public boolean credit;

    public WalletTransaction() {
    }

    public WalletTransaction(String id, String title, double amount, String statusKey) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.statusKey = statusKey;
    }
}
