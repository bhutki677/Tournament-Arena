package com.vk.tournamentapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.vk.tournamentapp.R;
import com.vk.tournamentapp.TournamentArenaApp;
import com.vk.tournamentapp.databinding.ActivityAuthBinding;

/**
 * Host for the whole authentication flow (login / register / reset / OTP). The screens
 * themselves are destinations of res/navigation/nav_auth.xml.
 */
public class AuthActivity extends AppCompatActivity {

    private ActivityAuthBinding binding;

    /** Called by the auth fragments once a session exists (phase 1C wires the call). */
    public static void enterMain(android.content.Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        applyWindowInsets();

        // Touch the session once so the mock session is initialised before the
        // first auth screen asks for it.
        TournamentArenaApp.get().session();
    }

    private void applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.authRoot, (view, windowInsets) -> {
            Insets bars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    @Nullable
    private NavController navController() {
        NavHostFragment host = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.auth_nav_host);
        return host == null ? null : host.getNavController();
    }
}
