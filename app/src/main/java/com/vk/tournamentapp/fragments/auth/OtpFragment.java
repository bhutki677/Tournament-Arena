package com.vk.tournamentapp.fragments.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vk.tournamentapp.R;
import com.vk.tournamentapp.TournamentArenaApp;
import com.vk.tournamentapp.activities.AuthActivity;
import com.vk.tournamentapp.databinding.FragmentOtpBinding;
import com.vk.tournamentapp.services.SessionManager;

/**
 * OTP scaffold. Phase 1C adds the countdown timer and verification through IAuthService;
 * for now "Verify" simply completes the mock session and enters the main shell.
 */
public class OtpFragment extends Fragment {

    private FragmentOtpBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOtpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SessionManager session = TournamentArenaApp.get().session();
        String email = session.getUserEmail();
        if (email == null || email.trim().isEmpty()) {
            email = getString(R.string.label_email);
        }

        binding.otpTitle.setText(R.string.auth_otp_title);
        binding.otpSubtitle.setText(getString(R.string.auth_otp_subtitle, email));
        binding.otpResendTimer.setText(R.string.auth_otp_resend);

        binding.otpResendButton.setOnClickListener(v ->
                binding.otpResendTimer.setText(R.string.auth_otp_resend));

        binding.otpSubmitButton.setOnClickListener(v -> {
            session.saveMockSession(session.getUserId(), getString(R.string.profile_guest_name));
            AuthActivity.enterMain(requireContext());
            if (getActivity() != null) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
