package com.subhi.arifalkora.data.repository

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("GameSettings", Context.MODE_PRIVATE)

    var isSoundEnabled: Boolean
        get() = prefs.getBoolean("sound", true)
        set(value) = prefs.edit().putBoolean("sound", value).apply()

    var isVibrationEnabled: Boolean
        get() = prefs.getBoolean("vibration", true)
        set(value) = prefs.edit().putBoolean("vibration", value).apply()

    // --- نظام الذاكرة لتتبع الأسئلة التي تم لعبها ---

    // جلب معرفات الأسئلة (IDs) التي لعبها المستخدم سابقاً في مستوى معين
    fun getPlayedQuestions(level: String): Set<String> {
        return prefs.getStringSet("played_$level", emptySet()) ?: emptySet()
    }

    // حفظ الأسئلة الجديدة التي ظهرت للمستخدم الآن لكي لا تظهر مجدداً
    fun savePlayedQuestions(level: String, newIds: Set<String>) {
        val current = getPlayedQuestions(level).toMutableSet()
        current.addAll(newIds)
        prefs.edit().putStringSet("played_$level", current).apply()
    }

    // تصفير الذاكرة (عندما ينهي اللاعب جميع أسئلة المستوى لتبدأ الدورة من جديد)
    fun resetPlayedQuestions(level: String) {
        prefs.edit().remove("played_$level").apply()
    }
}
