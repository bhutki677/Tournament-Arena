package com.vk.tournamentapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vk.tournamentapp.R;
import com.vk.tournamentapp.activities.NotificationsActivity;
import com.vk.tournamentapp.data.RepositoryProvider;
import com.vk.tournamentapp.databinding.FragmentHomeBinding;
import com.vk.tournamentapp.models.Tournament;
import com.vk.tournamentapp.repositories.RepositoryCallback;
import com.vk.tournamentapp.utils.StateController;

import java.util.List;

/**
 * Home destination. Phase 1A ships the full layout skeleton and the state machine;
 * phase 1B attaches the card adapters to the two RecyclerViews below.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private StateController stateController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.homeHeader.screenTitle.setText(R.string.title_home);
        binding.homeHeader.screenSubtitle.setText(R.string.subtitle_home);

        binding.homeLiveList.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.homeFeaturedList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.homeLiveList.setHasFixedSize(true);
        binding.homeFeaturedList.setHasFixedSize(true);

        stateController = new StateController(binding.homeSwipe, binding.homeLoading.getRoot(),
                binding.homeEmpty.getRoot(), binding.homeError.getRoot(),
                binding.homeEmpty.emptyTitle,
                binding.homeEmpty.emptyMessage, binding.homeError.errorMessage);

        binding.homeSwipe.setOnRefreshListener(this::loadTournaments);
        binding.homeError.errorRetryButton.setOnClickListener(v -> loadTournaments());
        binding.homeHeroAction.setOnClickListener(v -> openTab(R.id.nav_tournaments));
        binding.homeLiveSeeAll.setOnClickListener(v -> openTab(R.id.nav_tournaments));
        binding.homeQuickTournaments.setOnClickListener(v -> openTab(R.id.nav_tournaments));
        binding.homeQuickMatches.setOnClickListener(v -> openTab(R.id.nav_matches));
        binding.homeQuickWallet.setOnClickListener(v -> openTab(R.id.nav_wallet));
        binding.homeQuickNotifications.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), NotificationsActivity.class)));

        loadTournaments();
    }

    private void openTab(int destinationId) {
        try {
            Navigation.findNavController(requireView()).navigate(destinationId);
        } catch (IllegalArgumentException ignored) {
            // Destination not reachable from the current graph; safe to ignore in phase 1A.
        }
    }

    private void loadTournaments() {
        stateController.showLoading();
        RepositoryProvider.tournaments(requireContext()).getTournaments(
                new RepositoryCallback<List<Tournament>>() {
                    @Override
                    public void onSuccess(@NonNull List<Tournament> data) {
                        if (binding == null) {
                            return;
                        }
                        binding.homeSwipe.setRefreshing(false);
                        if (data.isEmpty()) {
                            stateController.showEmpty(R.string.empty_tournaments_title,
                                    R.string.empty_tournaments_message);
                        } else {
                            stateController.showContent();
                        }
                    }

                    @Override
                    public void onError(int messageResId) {
                        if (binding == null) {
                            return;
                        }
                        binding.homeSwipe.setRefreshing(false);
                        stateController.showError(messageResId != 0
                                ? messageResId : R.string.error_tournaments_message);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
