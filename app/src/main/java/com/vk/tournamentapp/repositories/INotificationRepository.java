package com.vk.tournamentapp.repositories;

import androidx.annotation.NonNull;

import com.vk.tournamentapp.models.AppNotification;

import java.util.List;

public interface INotificationRepository {

    void getNotifications(@NonNull RepositoryCallback<List<AppNotification>> callback);

    void markAllRead();
}
