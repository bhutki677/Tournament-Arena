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
import com.vk.tournamentapp.databinding.FragmentLoginBinding;

/**
 * Login scaffold. Phase 1C adds field validation (ValidationUtils is already in place),
 * the loading state and the real IAuthService call.
 */
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loginTitle.setText(R.string.auth_login_title);
        binding.loginSubtitle.setText(R.string.auth_login_subtitle);

        binding.loginRegisterButton.setOnClickListener(v -> navigate(R.id.action_login_to_register));
        binding.loginForgotButton.setOnClickListener(v ->
                navigate(R.id.action_login_to_forgot_password));
        binding.loginSubmitButton.setOnClickListener(v -> navigate(R.id.action_login_to_otp));
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
