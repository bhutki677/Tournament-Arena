package com.vk.tournamentapp.data;

import androidx.annotation.NonNull;

import com.vk.tournamentapp.models.WalletSummary;
import com.vk.tournamentapp.models.WalletTransaction;
import com.vk.tournamentapp.repositories.IWalletRepository;
import com.vk.tournamentapp.repositories.RepositoryCallback;

import java.util.Collections;
import java.util.List;

/** Phase 1A: zeroed summary and an empty ledger; phase 1B fills both. */
public class MockWalletRepository extends MockRepositoryBase implements IWalletRepository {

    @Override
    public void getWalletSummary(@NonNull RepositoryCallback<WalletSummary> callback) {
        deliver(new WalletSummary(0d, 0d, 0d), callback);
    }

    @Override
    public void getTransactions(@NonNull RepositoryCallback<List<WalletTransaction>> callback) {
        deliver(Collections.<WalletTransaction>emptyList(), callback);
    }
}
