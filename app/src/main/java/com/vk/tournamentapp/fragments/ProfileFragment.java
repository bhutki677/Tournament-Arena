package com.vk.tournamentapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.vk.tournamentapp.BuildConfig;
import com.vk.tournamentapp.R;
import com.vk.tournamentapp.TournamentArenaApp;
import com.vk.tournamentapp.activities.AuthActivity;
import com.vk.tournamentapp.activities.NotificationsActivity;
import com.vk.tournamentapp.databinding.FragmentProfileBinding;
import com.vk.tournamentapp.databinding.ViewMenuRowBinding;
import com.vk.tournamentapp.services.SessionManager;
import com.vk.tournamentapp.utils.StateController;

/** Profile destination: identity, stats and the account/preferences/support rows. */
public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private StateController stateController;
    private SessionManager session;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        session = TournamentArenaApp.get().session();
        binding.profileHeader.screenTitle.setText(R.string.title_profile);
        binding.profileHeader.screenSubtitle.setText(R.string.subtitle_profile);
        binding.profileVersion.setText(getString(R.string.app_version_format,
                BuildConfig.VERSION_NAME));

        stateController = new StateController(binding.profileScroll,
                binding.profileLoading.getRoot(),
                binding.profileEmpty.getRoot(), binding.profileError.getRoot(),
                binding.profileEmpty.emptyTitle,
                binding.profileEmpty.emptyMessage, binding.profileError.errorMessage);
        binding.profileError.errorRetryButton.setOnClickListener(v -> bindProfile());

        bindProfile();
    }

    private void bindProfile() {
        if (binding == null) {
            return;
        }
        stateController.showLoading();

        bindRow(binding.profileRowEditProfile, R.drawable.ic_edit,
                R.string.profile_edit_profile, R.string.profile_edit_profile_sub);
        bindRow(binding.profileRowNotifications, R.drawable.ic_bell,
                R.string.profile_notifications, R.string.profile_notifications_sub);
        bindRow(binding.profileRowSettings, R.drawable.ic_settings,
                R.string.profile_settings, R.string.profile_settings_sub);
        bindRow(binding.profileRowHelp, R.drawable.ic_help,
                R.string.profile_help, R.string.profile_help_sub);
        bindRow(binding.profileRowTerms, R.drawable.ic_receipt,
                R.string.profile_terms, R.string.profile_terms_sub);
        bindRow(binding.profileRowPrivacy, R.drawable.ic_shield,
                R.string.profile_privacy, R.string.profile_privacy_sub);
        bindRow(binding.profileRowLogout, R.drawable.ic_logout,
                R.string.profile_logout, R.string.profile_logout_sub);

        binding.profileRowNotifications.rowRoot.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), NotificationsActivity.class)));
        binding.profileRowLogout.rowRoot.setOnClickListener(v -> logOut());

        String name = session.getUserName();
        if (name == null || name.trim().isEmpty()) {
            binding.profileName.setText(R.string.profile_guest_name);
            binding.profileSubtitle.setText(R.string.profile_guest_sub);
            stateController.showEmpty(R.string.empty_profile_title, R.string.empty_profile_message);
        } else {
            binding.profileName.setText(name);
            String email = session.getUserEmail();
            if (email != null && !email.trim().isEmpty()) {
                binding.profileSubtitle.setText(email);
            }
            binding.profileStatPlayedValue.setText(R.string.label_dash);
            binding.profileStatWonValue.setText(R.string.label_dash);
            binding.profileStatWinrateValue.setText(R.string.label_dash);
            stateController.showContent();
        }
    }

    private void logOut() {
        session.logout();
        Intent intent = new Intent(requireContext(), AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    private void bindRow(@NonNull ViewMenuRowBinding row, @DrawableRes int iconRes,
                         @StringRes int titleRes, @StringRes int subtitleRes) {
        row.rowIcon.setImageResource(iconRes);
        TextView title = row.rowTitle;
        title.setText(titleRes);
        row.rowSubtitle.setText(subtitleRes);
        ImageView icon = row.rowIcon;
        icon.setContentDescription(getString(titleRes));
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
