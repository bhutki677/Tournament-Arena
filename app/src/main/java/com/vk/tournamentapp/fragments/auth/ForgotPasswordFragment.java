package com.vk.tournamentapp.fragments.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.vk.tournamentapp.R;
import com.vk.tournamentapp.databinding.FragmentForgotPasswordBinding;

/** Reset password scaffold. Phase 1C wires the reset request through IAuthService. */
public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.forgotTitle.setText(R.string.auth_forgot_title);
        binding.forgotSubtitle.setText(R.string.auth_forgot_subtitle);

        binding.forgotSubmitButton.setOnClickListener(v -> navigate(R.id.action_forgot_to_otp));
        binding.forgotBackButton.setOnClickListener(v -> navigate(R.id.action_forgot_to_login));
    }

    private void navigate(int actionId) {
        try {
            Navigation.findNavController(requireView()).navigate(actionId);
        } catch (IllegalArgumentException ignored) {
            // Graph edge missing; phase 1C wires the remaining transitions.
        }
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
