package com.vk.tournamentapp.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vk.tournamentapp.R;
import com.vk.tournamentapp.data.RepositoryProvider;
import com.vk.tournamentapp.databinding.ActivityTournamentDetailsBinding;
import com.vk.tournamentapp.models.Tournament;
import com.vk.tournamentapp.repositories.RepositoryCallback;
import com.vk.tournamentapp.utils.AppConstants;
import com.vk.tournamentapp.utils.StateController;

/**
 * Tournament details host. Phase 1D fills the content sections; the state machine and
 * repository wiring are already in place here.
 */
public class TournamentDetailsActivity extends AppCompatActivity {

    private ActivityTournamentDetailsBinding binding;
    private StateController stateController;
    private String tournamentId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTournamentDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tournamentId = getIntent().getStringExtra(AppConstants.ARG_TOURNAMENT_ID);

        stateController = new StateController(binding.detailsContent,
                binding.detailsLoading.getRoot(),
                binding.detailsEmpty.getRoot(), binding.detailsError.getRoot(),
                binding.detailsEmpty.emptyTitle,
                binding.detailsEmpty.emptyMessage, binding.detailsError.errorMessage);

        binding.detailsToolbar.setNavigationOnClickListener(view -> finish());
        binding.detailsError.errorRetryButton.setOnClickListener(view -> loadTournament());
        binding.detailsPrimaryAction.setOnClickListener(view -> loadTournament());

        loadTournament();
    }

    private void loadTournament() {
        stateController.showLoading();
        RepositoryProvider.tournaments(this).getTournamentById(
                tournamentId == null ? "" : tournamentId,
                new RepositoryCallback<Tournament>() {
                    @Override
                    public void onSuccess(Tournament data) {
                        stateController.showContent();
                    }

                    @Override
                    public void onError(int messageResId) {
                        stateController.showError(messageResId != 0
                                ? messageResId : R.string.error_tournaments_message);
                    }
                });
    }
}
