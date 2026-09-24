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
import com.vk.tournamentapp.databinding.FragmentRegisterBinding;

/** Register scaffold. Phase 1C adds validation and the register call. */
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.registerTitle.setText(R.string.auth_register_title);
        binding.registerSubtitle.setText(R.string.auth_register_subtitle);
        binding.registerTerms.setText(R.string.auth_terms_prefix);

        binding.registerLoginButton.setOnClickListener(v -> navigate(R.id.action_register_to_login));
        binding.registerSubmitButton.setOnClickListener(v -> navigate(R.id.action_register_to_otp));
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
