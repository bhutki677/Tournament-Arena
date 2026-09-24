package com.vk.tournamentapp.services;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vk.tournamentapp.utils.AppConstants;

/**
 * Mock session store. It keeps only a logged in flag plus a display name - never a
 * password or token. Phase 2 replaces the body of this class with real auth state.
 */
public class SessionManager {

    /** Where the app should go after the splash. Kept here as the single decision point. */
    public enum StartDestination {
        AUTH,
        MAIN
    }

    private final SharedPreferences preferences;

    public SessionManager(@NonNull Context context) {
        this.preferences = context.getApplicationContext()
                .getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isFirstLaunch() {
        return preferences.getBoolean(AppConstants.KEY_FIRST_LAUNCH, true);
    }

    public void markLaunched() {
        preferences.edit().putBoolean(AppConstants.KEY_FIRST_LAUNCH, false).apply();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(AppConstants.KEY_LOGGED_IN, false);
    }

    /** The single routing decision used by SplashActivity. */
    @NonNull
    public StartDestination decideStartDestination() {
        // Phase 2: replace this with a real session/token check.
        return isLoggedIn() ? StartDestination.MAIN : StartDestination.AUTH;
    }

    public void saveMockSession(@NonNull String userId, @NonNull String name) {
        preferences.edit()
                .putBoolean(AppConstants.KEY_LOGGED_IN, true)
                .putString(AppConstants.KEY_USER_ID, userId)
                .putString(AppConstants.KEY_USER_NAME, name)
                .apply();
    }

    public void saveLogin(@Nullable String userId, @Nullable String name, @Nullable String email) {
        preferences.edit()
                .putBoolean(AppConstants.KEY_LOGGED_IN, true)
                .putString(AppConstants.KEY_USER_ID, userId)
                .putString(AppConstants.KEY_USER_NAME, name)
                .putString(AppConstants.KEY_USER_EMAIL, email)
                .apply();
    }

    public void logout() {
        preferences.edit()
                .putBoolean(AppConstants.KEY_LOGGED_IN, false)
                .remove(AppConstants.KEY_USER_ID)
                .remove(AppConstants.KEY_USER_NAME)
                .remove(AppConstants.KEY_USER_EMAIL)
                .apply();
    }

    @NonNull
    public String getUserId() {
        return preferences.getString(AppConstants.KEY_USER_ID, AppConstants.MOCK_USER_ID);
    }

    @Nullable
    public String getUserName() {
        return preferences.getString(AppConstants.KEY_USER_NAME, null);
    }

    @Nullable
    public String getUserEmail() {
        return preferences.getString(AppConstants.KEY_USER_EMAIL, null);
    }
}
