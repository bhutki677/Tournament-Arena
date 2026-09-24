package com.vk.tournamentapp.services;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.vk.tournamentapp.R;
import com.vk.tournamentapp.models.User;
import com.vk.tournamentapp.utils.AppConstants;

/**
 * Phase 1A/1C mock: no network, no credentials stored anywhere, always succeeds.
 * Every result is posted on the main thread after a short delay so the UI can show
 * its loading state for real.
 */
public class MockAuthService implements IAuthService {

    private final Handler handler = new Handler(Looper.getMainLooper());

    private void complete(@NonNull final AuthCallback callback) {
        handler.postDelayed(() -> callback.onSuccess(mockUser()), AppConstants.MOCK_LOAD_DELAY_MS);
    }

    private static User mockUser() {
        User user = new User(AppConstants.MOCK_USER_ID, AppConstants.MOCK_USER_NAME,
                AppConstants.MOCK_USER_EMAIL);
        user.phone = AppConstants.MOCK_USER_PHONE;
        user.ign = AppConstants.MOCK_USER_IGN;
        return user;
    }

    @Override
    public void login(@NonNull String email, @NonNull String password,
                      @NonNull AuthCallback callback) {
        if (email.trim().isEmpty() || password.isEmpty()) {
            handler.postDelayed(() -> callback.onError(R.string.error_field_required),
                    AppConstants.MOCK_LOAD_DELAY_MS);
            return;
        }
        complete(callback);
    }

    @Override
    public void register(@NonNull String name, @NonNull String email, @NonNull String phone,
                         @NonNull String password, @NonNull AuthCallback callback) {
        complete(callback);
    }

    @Override
    public void requestPasswordReset(@NonNull String email, @NonNull AuthCallback callback) {
        complete(callback);
    }

    @Override
    public void verifyOtp(@NonNull String email, @NonNull String otp,
                          @NonNull AuthCallback callback) {
        complete(callback);
    }
}
