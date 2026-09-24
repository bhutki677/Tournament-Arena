package com.vk.tournamentapp.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.vk.tournamentapp.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/** Small formatting / dimension helpers shared by every screen. */
public final class AppUtils {

    private AppUtils() {
    }

    public static int dpToPx(@NonNull Context context, float dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }

    public static float pxToDp(@NonNull Context context, float px) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / metrics.density;
    }

    public static float spToPx(@NonNull Context context, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }

    /** Currency string built from the design system currency format. */
    public static String formatCurrency(@NonNull Context context, double amount) {
        NumberFormat format = NumberFormat.getNumberInstance(new Locale("en", "IN"));
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(2);
        return context.getString(R.string.header_currency_format, format.format(amount));
    }

    public static String formatCompactCurrency(@NonNull Context context, double amount) {
        String value;
        if (amount >= 10000000d) {
            value = String.format(Locale.US, "%.1fCr", amount / 10000000d);
        } else if (amount >= 100000d) {
            value = String.format(Locale.US, "%.1fL", amount / 100000d);
        } else if (amount >= 1000d) {
            value = String.format(Locale.US, "%.1fK", amount / 1000d);
        } else {
            value = String.format(Locale.US, "%.0f", amount);
        }
        return context.getString(R.string.header_currency_format, value);
    }

    public static String formatDate(long millis) {
        return new SimpleDateFormat(AppConstants.PATTERN_DATE, Locale.getDefault())
                .format(new Date(millis));
    }

    public static String formatShortDate(long millis) {
        return new SimpleDateFormat(AppConstants.PATTERN_SHORT_DATE, Locale.getDefault())
                .format(new Date(millis));
    }

    public static String formatTime(long millis) {
        return new SimpleDateFormat(AppConstants.PATTERN_TIME, Locale.getDefault())
                .format(new Date(millis));
    }

    public static String formatDateTime(long millis) {
        return new SimpleDateFormat(AppConstants.PATTERN_DATE_TIME, Locale.getDefault())
                .format(new Date(millis));
    }

    /**
     * Countdown helper used by CountdownView in phase 1B. Copy comes from strings.xml,
     * never from a literal in Java.
     */
    public static String formatCountdown(@NonNull Context context, long remainingMillis) {
        if (remainingMillis <= 0L) {
            return context.getString(R.string.countdown_ended);
        }
        long days = TimeUnit.MILLISECONDS.toDays(remainingMillis);
        long hours = TimeUnit.MILLISECONDS.toHours(remainingMillis) % 24L;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMillis) % 60L;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(remainingMillis) % 60L;

        if (days > 0L) {
            return context.getString(R.string.countdown_days_hours_format, days, hours);
        }
        if (hours > 0L) {
            return context.getString(R.string.countdown_hours_minutes_format, hours, minutes);
        }
        if (minutes > 0L) {
            return context.getString(R.string.countdown_minutes_format, minutes);
        }
        return context.getString(R.string.countdown_seconds_format, seconds);
    }

    /** Returns a string for the given resource id, tolerating 0 (no message). */
    public static String safeString(@NonNull Context context, @StringRes int resId,
                                    @StringRes int fallbackResId) {
        return context.getString(resId != 0 ? resId : fallbackResId);
    }
}
