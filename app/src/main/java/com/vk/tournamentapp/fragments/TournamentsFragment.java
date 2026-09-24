package com.vk.tournamentapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.chip.Chip;
import com.vk.tournamentapp.R;
import com.vk.tournamentapp.data.RepositoryProvider;
import com.vk.tournamentapp.databinding.FragmentTournamentsBinding;
import com.vk.tournamentapp.models.Tournament;
import com.vk.tournamentapp.repositories.RepositoryCallback;
import com.vk.tournamentapp.utils.StateController;

import java.util.List;

/**
 * Tournaments destination: search field, status chips and the list that phase 1B fills
 * with TournamentCard views.
 */
public class TournamentsFragment extends Fragment {

    private FragmentTournamentsBinding binding;
    private StateController stateController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTournamentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tournamentsHeader.screenTitle.setText(R.string.title_tournaments);
        binding.tournamentsHeader.screenSubtitle.setText(R.string.subtitle_tournaments);
        binding.tournamentsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.tournamentsList.setHasFixedSize(true);

        stateController = new StateController(binding.tournamentsContent, binding.tournamentsLoading,
                binding.tournamentsEmpty, binding.tournamentsError,
                binding.tournamentsEmpty.emptyTitle, binding.tournamentsEmpty.emptyMessage,
                binding.tournamentsError.errorMessage);

        binding.tournamentsFilterButton.setOnClickListener(v -> loadTournaments());
        binding.tournamentsError.errorRetryButton.setOnClickListener(v -> loadTournaments());

        View.OnClickListener chipListener = v -> selectChip((Chip) v);
        binding.tournamentsChipAll.setOnClickListener(chipListener);
        binding.tournamentsChipLive.setOnClickListener(chipListener);
        binding.tournamentsChipUpcoming.setOnClickListener(chipListener);
        binding.tournamentsChipCompleted.setOnClickListener(chipListener);

        loadTournaments();
    }

    private void selectChip(@NonNull Chip selected) {
        selected.setChecked(true);
        loadTournaments();
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
