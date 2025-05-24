package com.rayhan.mytask.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor: SharedPreferences.Editor = prefs.edit()

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_CURRENT_USERNAME = "current_username"
        private const val KEY_DARK_MODE = "dark_mode"
        private const val KEY_NOTIFICATION_ENABLED = "notification_enabled"
    }

    var isLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) {
            editor.putBoolean(KEY_IS_LOGGED_IN, value)
            editor.apply()
        }

    var currentUsername: String?
        get() = prefs.getString(KEY_CURRENT_USERNAME, null)
        set(value) {
            editor.putString(KEY_CURRENT_USERNAME, value)
            editor.apply()
        }

    var isDarkMode: Boolean
        get() = prefs.getBoolean(KEY_DARK_MODE, false)
        set(value) {
            editor.putBoolean(KEY_DARK_MODE, value)
            editor.apply()
        }

    var isNotificationEnabled: Boolean
        get() = prefs.getBoolean(KEY_NOTIFICATION_ENABLED, true)
        set(value) {
            editor.putBoolean(KEY_NOTIFICATION_ENABLED, value)
            editor.apply()
        }

    fun clearUserData() {
        editor.remove(KEY_IS_LOGGED_IN)
        editor.remove(KEY_CURRENT_USERNAME)
        editor.apply()
    }
}