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
import com.vk.tournamentapp.databinding.FragmentMatchesBinding;
import com.vk.tournamentapp.models.Match;
import com.vk.tournamentapp.repositories.RepositoryCallback;
import com.vk.tournamentapp.utils.StateController;

import java.util.List;

/** My Matches destination: joined and completed sections. */
public class MyMatchesFragment extends Fragment {

    private FragmentMatchesBinding binding;
    private StateController stateController;
    private boolean joinedLoaded;
    private boolean completedLoaded;
    private boolean anyMatch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMatchesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.matchesHeader.screenTitle.setText(R.string.title_matches);
        binding.matchesHeader.screenSubtitle.setText(R.string.subtitle_matches);

        LinearLayoutManager joinedManager = new LinearLayoutManager(requireContext());
        binding.matchesJoinedList.setLayoutManager(joinedManager);
        binding.matchesJoinedList.setHasFixedSize(true);
        binding.matchesCompletedList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.matchesCompletedList.setHasFixedSize(true);

        stateController = new StateController(binding.matchesScroll, binding.matchesLoading,
                binding.matchesEmpty, binding.matchesError, binding.matchesEmpty.emptyTitle,
                binding.matchesEmpty.emptyMessage, binding.matchesError.errorMessage);

        binding.matchesError.errorRetryButton.setOnClickListener(v -> loadMatches());
        binding.matchesChipJoined.setOnClickListener(v -> loadMatches());
        binding.matchesChipCompleted.setOnClickListener(v -> loadMatches());

        loadMatches();
    }

    private void loadMatches() {
        joinedLoaded = false;
        completedLoaded = false;
        anyMatch = false;
        stateController.showLoading();

        RepositoryProvider.matches(requireContext()).getJoinedMatches(
                new RepositoryCallback<List<Match>>() {
                    @Override
                    public void onSuccess(@NonNull List<Match> data) {
                        joinedLoaded = true;
                        anyMatch = anyMatch || !data.isEmpty();
                        settle();
                    }

                    @Override
                    public void onError(int messageResId) {
                        if (binding != null) {
                            stateController.showError(messageResId != 0
                                    ? messageResId : R.string.error_matches_message);
                        }
                    }
                });

        RepositoryProvider.matches(requireContext()).getCompletedMatches(
                new RepositoryCallback<List<Match>>() {
                    @Override
                    public void onSuccess(@NonNull List<Match> data) {
                        completedLoaded = true;
                        anyMatch = anyMatch || !data.isEmpty();
                        settle();
                    }

                    @Override
                    public void onError(int messageResId) {
                        if (binding != null) {
                            stateController.showError(messageResId != 0
                                    ? messageResId : R.string.error_matches_message);
                        }
                    }
                });
    }

    private void settle() {
        if (binding == null || !joinedLoaded || !completedLoaded) {
            return;
        }
        if (anyMatch) {
            stateController.showContent();
        } else {
            stateController.showEmpty(R.string.empty_matches_title,
                    R.string.empty_matches_message);
        }
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
