package com.vk.tournamentapp.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vk.tournamentapp.R;
import com.vk.tournamentapp.data.RepositoryProvider;
import com.vk.tournamentapp.databinding.ActivityNotificationsBinding;
import com.vk.tournamentapp.models.AppNotification;
import com.vk.tournamentapp.repositories.RepositoryCallback;
import com.vk.tournamentapp.utils.StateController;

import java.util.List;

/** Notifications host. Phase 1E adds the adapter and the real list content. */
public class NotificationsActivity extends AppCompatActivity {

    private ActivityNotificationsBinding binding;
    private StateController stateController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.notificationsList.setLayoutManager(new LinearLayoutManager(this));
        binding.notificationsList.setHasFixedSize(true);

        stateController = new StateController(binding.notificationsContent,
                binding.notificationsLoading.getRoot(), binding.notificationsEmpty.getRoot(),
                binding.notificationsError.getRoot(), binding.notificationsEmpty.emptyTitle,
                binding.notificationsEmpty.emptyMessage, binding.notificationsError.errorMessage);

        binding.notificationsToolbar.setNavigationOnClickListener(view -> finish());
        binding.notificationsToolbar.setOnMenuItemClickListener(this::onMenuItemSelected);
        binding.notificationsError.errorRetryButton.setOnClickListener(view -> loadNotifications());

        loadNotifications();
    }

    private boolean onMenuItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_mark_all_read) {
            RepositoryProvider.notifications(this).markAllRead();
            return true;
        }
        return false;
    }

    private void loadNotifications() {
        stateController.showLoading();
        RepositoryProvider.notifications(this).getNotifications(
                new RepositoryCallback<List<AppNotification>>() {
                    @Override
                    public void onSuccess(@NonNull List<AppNotification> data) {
                        if (data.isEmpty()) {
                            stateController.showEmpty(R.string.empty_notifications_title,
                                    R.string.empty_notifications_message);
                        } else {
                            stateController.showContent();
                        }
                    }

                    @Override
                    public void onError(int messageResId) {
                        stateController.showError(messageResId != 0
                                ? messageResId : R.string.error_notifications_message);
                    }
                });
    }
}
