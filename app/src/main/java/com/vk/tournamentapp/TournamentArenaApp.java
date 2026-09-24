package com.vk.tournamentapp;

import android.app.Application;

import androidx.annotation.NonNull;

import com.vk.tournamentapp.data.RepositoryProvider;
import com.vk.tournamentapp.services.IAuthService;
import com.vk.tournamentapp.services.MockAuthService;
import com.vk.tournamentapp.services.SessionManager;

/**
 * Application entry point. Owns the process wide singletons (session + auth service)
 * so screens receive them instead of creating their own.
 */
public class TournamentArenaApp extends Application {

    private static TournamentArenaApp instance;

    private SessionManager sessionManager;
    private IAuthService authService;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sessionManager = new SessionManager(this);
        authService = new MockAuthService();
        RepositoryProvider.reset();
    }

    @NonNull
    public static TournamentArenaApp get() {
        return instance;
    }

    @NonNull
    public SessionManager session() {
        return sessionManager;
    }

    @NonNull
    public IAuthService authService() {
        return authService;
    }
}
