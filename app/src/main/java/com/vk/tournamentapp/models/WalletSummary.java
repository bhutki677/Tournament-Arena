package com.vk.tournamentapp.models;

/** Aggregated wallet figures shown on the wallet header card. */
public class WalletSummary {

    public double availableBalance;
    public double lockedBalance;
    public double totalWinnings;
    public double totalDeposits;

    public WalletSummary() {
    }

    public WalletSummary(double availableBalance, double lockedBalance, double totalWinnings) {
        this.availableBalance = availableBalance;
        this.lockedBalance = lockedBalance;
        this.totalWinnings = totalWinnings;
    }
}
