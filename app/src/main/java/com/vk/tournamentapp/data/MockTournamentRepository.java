package com.vk.tournamentapp.data;

import androidx.annotation.NonNull;

import com.vk.tournamentapp.R;
import com.vk.tournamentapp.models.Tournament;
import com.vk.tournamentapp.repositories.ITournamentRepository;
import com.vk.tournamentapp.repositories.RepositoryCallback;

import java.util.Collections;
import java.util.List;

/** Phase 1A: returns empty lists so every screen renders its real empty state. */
public class MockTournamentRepository extends MockRepositoryBase implements ITournamentRepository {

    @Override
    public void getTournaments(@NonNull RepositoryCallback<List<Tournament>> callback) {
        deliver(Collections.<Tournament>emptyList(), callback);
    }

    @Override
    public void getLiveTournaments(@NonNull RepositoryCallback<List<Tournament>> callback) {
        deliver(Collections.<Tournament>emptyList(), callback);
    }

    @Override
    public void getTournamentById(@NonNull String tournamentId,
                                  @NonNull RepositoryCallback<Tournament> callback) {
        deliverError(R.string.error_tournaments_message, callback);
    }
}
