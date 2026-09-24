package com.vk.tournamentapp.repositories;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * Single async callback shape shared by every repository so the UI layer can be
 * swapped onto real network services without changing a single screen.
 */
public interface RepositoryCallback<T> {

    void onSuccess(@NonNull T data);

    void onError(@StringRes int messageResId);
}
