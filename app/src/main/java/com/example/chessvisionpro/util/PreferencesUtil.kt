package com.example.chessvisionpro.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object PreferencesUtil {
    private const val PREFERENCES_NAME = "chess_vision_pro"
    private const val KEY_API_TOKEN = "lichess_api_token"
    private const val KEY_USERNAME = "username"
    private const val KEY_LAST_LOGIN = "last_login"
    private const val KEY_THEME = "theme"
    private const val KEY_AUTO_UPDATE = "auto_update"

    private fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            PREFERENCES_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun setApiToken(context: Context, token: String) {
        val prefs = getEncryptedSharedPreferences(context)
        prefs.edit().putString(KEY_API_TOKEN, token).apply()
    }

    fun getApiToken(context: Context): String? {
        val prefs = getEncryptedSharedPreferences(context)
        return prefs.getString(KEY_API_TOKEN, null)
    }

    fun clearApiToken(context: Context) {
        val prefs = getEncryptedSharedPreferences(context)
        prefs.edit().remove(KEY_API_TOKEN).apply()
    }

    fun setUsername(context: Context, username: String) {
        val prefs = getEncryptedSharedPreferences(context)
        prefs.edit().putString(KEY_USERNAME, username).apply()
    }

    fun getUsername(context: Context): String? {
        val prefs = getEncryptedSharedPreferences(context)
        return prefs.getString(KEY_USERNAME, null)
    }

    fun setLastLogin(context: Context, timestamp: Long) {
        val prefs = getEncryptedSharedPreferences(context)
        prefs.edit().putLong(KEY_LAST_LOGIN, timestamp).apply()
    }

    fun getLastLogin(context: Context): Long {
        val prefs = getEncryptedSharedPreferences(context)
        return prefs.getLong(KEY_LAST_LOGIN, 0)
    }

    fun setTheme(context: Context, theme: String) {
        val prefs = getEncryptedSharedPreferences(context)
        prefs.edit().putString(KEY_THEME, theme).apply()
    }

    fun getTheme(context: Context): String {
        val prefs = getEncryptedSharedPreferences(context)
        return prefs.getString(KEY_THEME, "auto") ?: "auto"
    }

    fun setAutoUpdate(context: Context, enabled: Boolean) {
        val prefs = getEncryptedSharedPreferences(context)
        prefs.edit().putBoolean(KEY_AUTO_UPDATE, enabled).apply()
    }

    fun isAutoUpdateEnabled(context: Context): Boolean {
        val prefs = getEncryptedSharedPreferences(context)
        return prefs.getBoolean(KEY_AUTO_UPDATE, true)
    }

    fun clearAll(context: Context) {
        val prefs = getEncryptedSharedPreferences(context)
        prefs.edit().clear().apply()
    }
}