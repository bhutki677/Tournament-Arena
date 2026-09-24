package com.vk.tournamentapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.vk.tournamentapp.R;
import com.vk.tournamentapp.databinding.ActivityMainBinding;

/**
 * The single activity that hosts the five top level destinations. Navigation is entirely
 * driven by res/navigation/nav_main.xml plus the nav item ids in res/menu/bottom_nav_menu.xml.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        applyWindowInsets();
        setUpNavigation();
        setUpToolbarMenu();
    }

    private void setUpNavigation() {
        NavHostFragment host = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_nav_host);
        if (host == null) {
            return;
        }
        navController = host.getNavController();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_tournaments, R.id.nav_matches,
                R.id.nav_wallet, R.id.nav_profile).build();

        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNav, navController);
    }

    private void setUpToolbarMenu() {
        binding.toolbar.setOnMenuItemClickListener(this::onToolbarMenuItemSelected);
    }

    private boolean onToolbarMenuItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_notifications) {
            startActivity(new Intent(this, NotificationsActivity.class));
            return true;
        }
        if (itemId == R.id.action_search) {
            if (navController != null && navController.getCurrentDestination() != null
                    && navController.getCurrentDestination().getId() != R.id.nav_tournaments) {
                navController.navigate(R.id.nav_tournaments);
            }
            return true;
        }
        return false;
    }

    private void applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainRoot, (view, windowInsets) -> {
            Insets bars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            binding.toolbar.setPadding(binding.toolbar.getPaddingLeft(), bars.top,
                    binding.toolbar.getPaddingRight(), binding.toolbar.getPaddingBottom());
            binding.bottomNav.setPadding(binding.bottomNav.getPaddingLeft(),
                    binding.bottomNav.getPaddingTop(),
                    binding.bottomNav.getPaddingRight(), bars.bottom);
            return windowInsets;
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return (navController != null && NavigationUI.navigateUp(navController,
                new AppBarConfiguration.Builder(R.id.nav_home).build()))
                || super.onSupportNavigateUp();
    }
}
