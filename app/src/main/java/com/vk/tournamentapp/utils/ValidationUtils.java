package com.vk.tournamentapp.utils;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.vk.tournamentapp.R;

/**
 * Field validators. Every method returns 0 when the value is valid or the string
 * resource id of the message to display. Phase 1C wires these into the auth forms.
 */
public final class ValidationUtils {

    private ValidationUtils() {
    }

    @StringRes
    public static int validateName(@Nullable String name) {
        String value = name == null ? "" : name.trim();
        if (TextUtils.isEmpty(value)) {
            return R.string.error_name_required;
        }
        if (value.length() < AppConstants.NAME_MIN_LENGTH) {
            return R.string.error_name_short;
        }
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (!Character.isLetter(c) && c != ' ') {
                return R.string.error_name_invalid;
            }
        }
        return 0;
    }

    @StringRes
    public static int validateEmail(@Nullable String email) {
        String value = email == null ? "" : email.trim();
        if (TextUtils.isEmpty(value)) {
            return R.string.error_email_required;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            return R.string.error_email_invalid;
        }
        return 0;
    }

    @StringRes
    public static int validatePhone(@Nullable String phone) {
        String value = phone == null ? "" : phone.trim();
        if (TextUtils.isEmpty(value)) {
            return R.string.error_phone_required;
        }
        if (value.length() != AppConstants.PHONE_LENGTH || !TextUtils.isDigitsOnly(value)) {
            return R.string.error_phone_invalid;
        }
        return 0;
    }

    @StringRes
    public static int validatePassword(@Nullable String password) {
        String value = password == null ? "" : password;
        if (TextUtils.isEmpty(value)) {
            return R.string.error_password_required;
        }
        if (value.length() < AppConstants.PASSWORD_MIN_LENGTH) {
            return R.string.error_password_short;
        }
        boolean hasLetter = false;
        boolean hasDigit = false;
        for (int i = 0; i < value.length(); i++) {
            if (Character.isLetter(value.charAt(i))) {
                hasLetter = true;
            } else if (Character.isDigit(value.charAt(i))) {
                hasDigit = true;
            }
        }
        if (!hasLetter || !hasDigit) {
            return R.string.error_password_weak;
        }
        return 0;
    }

    @StringRes
    public static int validateConfirmPassword(@Nullable String password, @Nullable String confirm) {
        if (TextUtils.isEmpty(confirm)) {
            return R.string.error_confirm_password_required;
        }
        if (!confirm.equals(password)) {
            return R.string.error_confirm_password_mismatch;
        }
        return 0;
    }

    @StringRes
    public static int validateOtp(@Nullable String otp) {
        String value = otp == null ? "" : otp.trim();
        if (TextUtils.isEmpty(value)) {
            return R.string.error_otp_required;
        }
        if (value.length() != AppConstants.OTP_LENGTH || !TextUtils.isDigitsOnly(value)) {
            return R.string.error_otp_invalid;
        }
        return 0;
    }
}
