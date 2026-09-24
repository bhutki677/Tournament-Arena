package com.vk.tournamentapp.repositories;

import androidx.annotation.NonNull;

import com.vk.tournamentapp.models.WalletSummary;
import com.vk.tournamentapp.models.WalletTransaction;

import java.util.List;

public interface IWalletRepository {

    void getWalletSummary(@NonNull RepositoryCallback<WalletSummary> callback);

    void getTransactions(@NonNull RepositoryCallback<List<WalletTransaction>> callback);
}
