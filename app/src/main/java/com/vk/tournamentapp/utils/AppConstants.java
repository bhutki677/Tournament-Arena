package com.vk.tournamentapp.utils;

/** Central place for non visual constants. No user facing text lives here. */
public final class AppConstants {

    private AppConstants() {
    }

    /* SharedPreferences */
    public static final String PREFS_NAME = "tournament_arena_prefs";
    public static final String KEY_LOGGED_IN = "key_logged_in";
    public static final String KEY_FIRST_LAUNCH = "key_first_launch";
    public static final String KEY_USER_ID = "key_user_id";
    public static final String KEY_USER_NAME = "key_user_name";
    public static final String KEY_USER_EMAIL = "key_user_email";
    public static final String KEY_USER_PHONE = "key_user_phone";
    public static final String KEY_USER_IGN = "key_user_ign";

    /* Splash / mock timing */
    public static final long SPLASH_MIN_DURATION_MS = 2200L;
    public static final long MOCK_LOAD_DELAY_MS = 700L;

    /* Validation rules (mirrored by strings/error_* messages) */
    public static final int NAME_MIN_LENGTH = 3;
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PHONE_LENGTH = 10;
    public static final int OTP_LENGTH = 6;

    /* Formatting patterns (patterns only - the copy comes from strings.xml) */
    public static final String PATTERN_DATE = "dd MMM yyyy";
    public static final String PATTERN_TIME = "hh:mm a";
    public static final String PATTERN_DATE_TIME = "dd MMM yyyy, hh:mm a";
    public static final String PATTERN_SHORT_DATE = "dd MMM";

    /* Intent / bundle argument keys */
    public static final String ARG_TOURNAMENT_ID = "arg_tournament_id";
    public static final String ARG_NOTIFICATION_ID = "arg_notification_id";

    /* Mock identifiers used until real services arrive */
    public static final String MOCK_USER_ID = "mock-player-001";
    public static final String MOCK_USER_NAME = "Arena Player";
    public static final String MOCK_USER_EMAIL = "player@tournamentarena.app";
    public static final String MOCK_USER_PHONE = "9000000000";
    public static final String MOCK_USER_IGN = "ArenaPlayer";
}
