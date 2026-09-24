package com.vk.tournamentapp.data;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.vk.tournamentapp.repositories.RepositoryCallback;
import com.vk.tournamentapp.utils.AppConstants;

/**
 * Shared mock plumbing: every mock answers on the main thread after a short delay so
 * loading states are exercised exactly like a real network call would exercise them.
 */
public abstract class MockRepositoryBase {

    private final Handler handler = new Handler(Looper.getMainLooper());

    protected <T> void deliver(@NonNull final T payload,
                               @NonNull final RepositoryCallback<T> callback) {
        handler.postDelayed(() -> callback.onSuccess(payload), AppConstants.MOCK_LOAD_DELAY_MS);
    }

    protected <T> void deliverError(@StringRes final int messageResId,
                                    @NonNull final RepositoryCallback<T> callback) {
        handler.postDelayed(() -> callback.onError(messageResId), AppConstants.MOCK_LOAD_DELAY_MS);
    }
}
