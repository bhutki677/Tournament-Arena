package com.vk.tournamentapp.repositories;

import androidx.annotation.NonNull;

import com.vk.tournamentapp.models.Tournament;

import java.util.List;

public interface ITournamentRepository {

    void getTournaments(@NonNull RepositoryCallback<List<Tournament>> callback);

    void getLiveTournaments(@NonNull RepositoryCallback<List<Tournament>> callback);

    void getTournamentById(@NonNull String tournamentId,
                           @NonNull RepositoryCallback<Tournament> callback);
}
