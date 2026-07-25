package com.example.chessvisionpro.util

import android.content.Context
import android.content.SharedPreferences
import com.example.chessvisionpro.util.Constants.SHARED_PREF_NAME
import com.example.chessvisionpro.util.Constants.SHARED_PREF_TOKEN_KEY
import com.example.chessvisionpro.util.Constants.SHARED_PREF_USERNAME_KEY

class PreferencesManager(context: Context) {
    private val sharedPref: SharedPreferences = context.getSharedPreferences(
        SHARED_PREF_NAME,
        Context.MODE_PRIVATE
    )

    fun saveApiToken(token: String) {
        sharedPref.edit().putString(SHARED_PREF_TOKEN_KEY, token).apply()
    }

    fun getApiToken(): String? {
        return sharedPref.getString(SHARED_PREF_TOKEN_KEY, null)
    }

    fun clearApiToken() {
        sharedPref.edit().remove(SHARED_PREF_TOKEN_KEY).apply()
    }

    fun saveUsername(username: String) {
        sharedPref.edit().putString(SHARED_PREF_USERNAME_KEY, username).apply()
    }

    fun getUsername(): String? {
        return sharedPref.getString(SHARED_PREF_USERNAME_KEY, null)
    }

    fun clearAll() {
        sharedPref.edit().clear().apply()
    }
}
