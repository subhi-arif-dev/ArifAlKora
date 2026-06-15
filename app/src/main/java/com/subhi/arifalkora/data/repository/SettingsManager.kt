package com.subhi.arifalkora.data.repository

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("GameSettings", Context.MODE_PRIVATE)

    var isSoundEnabled: Boolean
        get() = prefs.getBoolean("sound", false) // تم الإرجاع لـ false
        set(value) = prefs.edit().putBoolean("sound", value).apply()

    var isVibrationEnabled: Boolean
        get() = prefs.getBoolean("vibration", false) // تم الإرجاع لـ false
        set(value) = prefs.edit().putBoolean("vibration", value).apply()

    fun getPlayedQuestions(level: String): Set<String> {
        return prefs.getStringSet("played_$level", emptySet()) ?: emptySet()
    }

    fun savePlayedQuestions(level: String, newIds: Set<String>) {
        val current = getPlayedQuestions(level).toMutableSet()
        current.addAll(newIds)
        prefs.edit().putStringSet("played_$level", current).apply()
    }

    fun resetPlayedQuestions(level: String) {
        prefs.edit().remove("played_$level").apply()
    }
}
