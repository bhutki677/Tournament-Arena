package com.vk.tournamentapp.data;

import androidx.annotation.NonNull;

import com.vk.tournamentapp.models.Match;
import com.vk.tournamentapp.repositories.IMatchRepository;
import com.vk.tournamentapp.repositories.RepositoryCallback;

import java.util.Collections;
import java.util.List;

/** Phase 1A: returns empty lists so every screen renders its real empty state. */
public class MockMatchRepository extends MockRepositoryBase implements IMatchRepository {

    @Override
    public void getJoinedMatches(@NonNull RepositoryCallback<List<Match>> callback) {
        deliver(Collections.<Match>emptyList(), callback);
    }

    @Override
    public void getCompletedMatches(@NonNull RepositoryCallback<List<Match>> callback) {
        deliver(Collections.<Match>emptyList(), callback);
    }
}
