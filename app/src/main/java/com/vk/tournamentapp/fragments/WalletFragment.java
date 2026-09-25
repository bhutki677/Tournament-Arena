package com.vk.tournamentapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vk.tournamentapp.R;
import com.vk.tournamentapp.data.RepositoryProvider;
import com.vk.tournamentapp.databinding.FragmentWalletBinding;
import com.vk.tournamentapp.models.WalletSummary;
import com.vk.tournamentapp.models.WalletTransaction;
import com.vk.tournamentapp.repositories.RepositoryCallback;
import com.vk.tournamentapp.utils.AppUtils;
import com.vk.tournamentapp.utils.StateController;

import java.util.List;

/** Wallet destination: balance card, wallet actions and the transaction list. */
public class WalletFragment extends Fragment {

    private FragmentWalletBinding binding;
    private StateController stateController;
    private boolean summaryLoaded;
    private boolean transactionsLoaded;
    private boolean hasTransactions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentWalletBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.walletHeader.screenTitle.setText(R.string.title_wallet);
        binding.walletHeader.screenSubtitle.setText(R.string.subtitle_wallet);
        binding.walletTransactionsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.walletTransactionsList.setHasFixedSize(true);

        stateController = new StateController(binding.walletSwipe, binding.walletLoading.getRoot(),
                binding.walletEmpty.getRoot(), binding.walletError.getRoot(),
                binding.walletEmpty.emptyTitle,
                binding.walletEmpty.emptyMessage, binding.walletError.errorMessage);

        binding.walletSwipe.setOnRefreshListener(this::loadWallet);
        binding.walletError.errorRetryButton.setOnClickListener(v -> loadWallet());
        binding.walletDepositButton.setOnClickListener(v -> loadWallet());
        binding.walletWithdrawButton.setOnClickListener(v -> loadWallet());

        loadWallet();
    }

    private void loadWallet() {
        summaryLoaded = false;
        transactionsLoaded = false;
        hasTransactions = false;
        stateController.showLoading();

        RepositoryProvider.wallet(requireContext()).getWalletSummary(
                new RepositoryCallback<WalletSummary>() {
                    @Override
                    public void onSuccess(@NonNull WalletSummary data) {
                        if (binding == null) {
                            return;
                        }
                        binding.walletBalanceValue.setText(
                                AppUtils.formatCurrency(requireContext(), data.availableBalance));
                        binding.walletLockedValue.setText(
                                AppUtils.formatCurrency(requireContext(), data.lockedBalance));
                        binding.walletWinningsValue.setText(
                                AppUtils.formatCurrency(requireContext(), data.totalWinnings));
                        summaryLoaded = true;
                        settle();
                    }

                    @Override
                    public void onError(int messageResId) {
                        if (binding != null) {
                            binding.walletSwipe.setRefreshing(false);
                            stateController.showError(messageResId != 0
                                    ? messageResId : R.string.error_wallet_message);
                        }
                    }
                });

        RepositoryProvider.wallet(requireContext()).getTransactions(
                new RepositoryCallback<List<WalletTransaction>>() {
                    @Override
                    public void onSuccess(@NonNull List<WalletTransaction> data) {
                        hasTransactions = !data.isEmpty();
                        transactionsLoaded = true;
                        settle();
                    }

                    @Override
                    public void onError(int messageResId) {
                        if (binding != null) {
                            stateController.showError(messageResId != 0
                                    ? messageResId : R.string.error_wallet_message);
                        }
                    }
                });
    }

    private void settle() {
        if (binding == null || !summaryLoaded || !transactionsLoaded) {
            return;
        }
        binding.walletSwipe.setRefreshing(false);
        if (hasTransactions) {
            stateController.showContent();
        } else {
            stateController.showEmpty(R.string.empty_wallet_title, R.string.empty_wallet_message);
        }
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
