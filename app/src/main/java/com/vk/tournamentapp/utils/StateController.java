package com.vk.tournamentapp.utils;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

/**
 * Keeps the four screen states (loading / content / empty / error) consistent on
 * every screen. Fragments and activities all drive their states through this.
 */
public final class StateController {

    private final View content;
    private final View loading;
    private final View empty;
    private final View error;
    private final TextView emptyTitle;
    private final TextView emptyMessage;
    private final TextView errorMessage;

    public StateController(@NonNull View content,
                           @Nullable View loading,
                           @Nullable View empty,
                           @Nullable View error,
                           @Nullable TextView emptyTitle,
                           @Nullable TextView emptyMessage,
                           @Nullable TextView errorMessage) {
        this.content = content;
        this.loading = loading;
        this.empty = empty;
        this.error = error;
        this.emptyTitle = emptyTitle;
        this.emptyMessage = emptyMessage;
        this.errorMessage = errorMessage;
    }

    public void showContent() {
        setVisible(content, true);
        setVisible(loading, false);
        setVisible(empty, false);
        setVisible(error, false);
    }

    public void showLoading() {
        setVisible(content, false);
        setVisible(loading, true);
        setVisible(empty, false);
        setVisible(error, false);
    }

    public void showEmpty(@StringRes int titleRes, @StringRes int messageRes) {
        if (emptyTitle != null && titleRes != 0) {
            emptyTitle.setText(titleRes);
        }
        if (emptyMessage != null && messageRes != 0) {
            emptyMessage.setText(messageRes);
        }
        setVisible(content, false);
        setVisible(loading, false);
        setVisible(empty, true);
        setVisible(error, false);
    }

    public void showError(@StringRes int messageRes) {
        if (errorMessage != null && messageRes != 0) {
            errorMessage.setText(messageRes);
        }
        setVisible(content, false);
        setVisible(loading, false);
        setVisible(empty, false);
        setVisible(error, true);
    }

    private static void setVisible(@Nullable View view, boolean visible) {
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }
}
