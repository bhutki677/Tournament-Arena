package com.vk.tournamentapp.activities;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.vk.tournamentapp.R;
import com.vk.tournamentapp.TournamentArenaApp;
import com.vk.tournamentapp.databinding.ActivitySplashBinding;
import com.vk.tournamentapp.services.SessionManager;
import com.vk.tournamentapp.utils.AppConstants;

/**
 * Animated splash. All motion comes from res/anim and res/animator resources - there is
 * no UI thread sleep anywhere. The routing decision lives in SessionManager.
 */
public class SplashActivity extends AppCompatActivity {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable routeRunnable = this::routeToNextScreen;

    private ActivitySplashBinding binding;
    private Animator glowAnimator;
    private Animator logoPulseAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        startEntranceAnimations();
        handler.postDelayed(routeRunnable, AppConstants.SPLASH_MIN_DURATION_MS);
    }

    private void startEntranceAnimations() {
        Animation logoEnter = AnimationUtils.loadAnimation(this, R.anim.splash_logo_enter);
        binding.splashLogo.startAnimation(logoEnter);

        Animation contentEnter = AnimationUtils.loadAnimation(this, R.anim.splash_content_fade_up);
        binding.splashContent.startAnimation(contentEnter);

        Animation footerEnter = AnimationUtils.loadAnimation(this, R.anim.splash_content_fade_up);
        binding.splashFooter.startAnimation(footerEnter);

        Animation sweep = AnimationUtils.loadAnimation(this, R.anim.splash_shimmer_translate);
        sweep.setRepeatCount(Animation.INFINITE);
        binding.splashShimmer.startAnimation(sweep);

        glowAnimator = AnimatorInflater.loadAnimator(this, R.animator.splash_glow_pulse);
        glowAnimator.setTarget(binding.splashGlow);
        glowAnimator.start();

        logoPulseAnimator = AnimatorInflater.loadAnimator(this, R.animator.splash_logo_pulse);
        logoPulseAnimator.setTarget(binding.splashLogo);
        logoPulseAnimator.start();
    }

    /** Single place where the post-splash destination is decided. */
    private void routeToNextScreen() {
        SessionManager session = TournamentArenaApp.get().session();
        session.markLaunched();

        Class<?> destination = session.decideStartDestination() == SessionManager.StartDestination.MAIN
                ? MainActivity.class
                : AuthActivity.class;

        startActivity(new Intent(this, destination));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(routeRunnable);
        if (glowAnimator != null) {
            glowAnimator.cancel();
        }
        if (logoPulseAnimator != null) {
            logoPulseAnimator.cancel();
        }
        binding = null;
        super.onDestroy();
    }
}
