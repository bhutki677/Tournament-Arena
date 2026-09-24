package com.vk.tournamentapp.services;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.vk.tournamentapp.models.User;

/**
 * Authentication contract. Phase 2 swaps MockAuthService for a real implementation;
 * no screen ever references the mock directly.
 */
public interface IAuthService {

    interface AuthCallback {

        void onSuccess(@NonNull User user);

        void onError(@StringRes int messageResId);
    }

    void login(@NonNull String email, @NonNull String password, @NonNull AuthCallback callback);

    void register(@NonNull String name, @NonNull String email, @NonNull String phone,
                  @NonNull String password, @NonNull AuthCallback callback);

    void requestPasswordReset(@NonNull String email, @NonNull AuthCallback callback);

    void verifyOtp(@NonNull String email, @NonNull String otp, @NonNull AuthCallback callback);
}
