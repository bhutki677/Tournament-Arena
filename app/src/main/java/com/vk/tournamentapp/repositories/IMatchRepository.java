package com.vk.tournamentapp.repositories;

import androidx.annotation.NonNull;

import com.vk.tournamentapp.models.Match;

import java.util.List;

public interface IMatchRepository {

    void getJoinedMatches(@NonNull RepositoryCallback<List<Match>> callback);

    void getCompletedMatches(@NonNull RepositoryCallback<List<Match>> callback);
}
