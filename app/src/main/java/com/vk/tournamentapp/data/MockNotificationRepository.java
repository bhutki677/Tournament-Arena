package com.vk.tournamentapp.data;

import androidx.annotation.NonNull;

import com.vk.tournamentapp.models.AppNotification;
import com.vk.tournamentapp.repositories.INotificationRepository;
import com.vk.tournamentapp.repositories.RepositoryCallback;

import java.util.Collections;
import java.util.List;

/** Phase 1A: empty inbox; phase 1E fills it with mock notifications. */
public class MockNotificationRepository extends MockRepositoryBase
        implements INotificationRepository {

    @Override
    public void getNotifications(@NonNull RepositoryCallback<List<AppNotification>> callback) {
        deliver(Collections.<AppNotification>emptyList(), callback);
    }

    @Override
    public void markAllRead() {
        // No-op in phase 1A: nothing is stored locally yet.
    }
}
