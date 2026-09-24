package com.vk.tournamentapp.data;

import android.content.Context;

import androidx.annotation.NonNull;

import com.vk.tournamentapp.repositories.IMatchRepository;
import com.vk.tournamentapp.repositories.INotificationRepository;
import com.vk.tournamentapp.repositories.ITournamentRepository;
import com.vk.tournamentapp.repositories.IWalletRepository;

/**
 * Single access point for repository instances. Screens never new up a data source
 * themselves, so swapping the mocks for real services is a one file change.
 */
public final class RepositoryProvider {

    private static ITournamentRepository tournamentRepository;
    private static IMatchRepository matchRepository;
    private static IWalletRepository walletRepository;
    private static INotificationRepository notificationRepository;

    private RepositoryProvider() {
    }

    @NonNull
    public static synchronized ITournamentRepository tournaments(@NonNull Context context) {
        if (tournamentRepository == null) {
            tournamentRepository = new MockTournamentRepository();
        }
        return tournamentRepository;
    }

    @NonNull
    public static synchronized IMatchRepository matches(@NonNull Context context) {
        if (matchRepository == null) {
            matchRepository = new MockMatchRepository();
        }
        return matchRepository;
    }

    @NonNull
    public static synchronized IWalletRepository wallet(@NonNull Context context) {
        if (walletRepository == null) {
            walletRepository = new MockWalletRepository();
        }
        return walletRepository;
    }

    @NonNull
    public static synchronized INotificationRepository notifications(@NonNull Context context) {
        if (notificationRepository == null) {
            notificationRepository = new MockNotificationRepository();
        }
        return notificationRepository;
    }

    /** Used by tests and by phase 2 bootstrapping. */
    public static synchronized void reset() {
        tournamentRepository = null;
        matchRepository = null;
        walletRepository = null;
        notificationRepository = null;
    }
}
