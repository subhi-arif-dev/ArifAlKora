package com.subhi.arifalkora.data.repository

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("ArifAlKoraSettings", Context.MODE_PRIVATE)

    // القيمة الافتراضية الآن هي false (مغلق)
    var isSoundEnabled: Boolean
        get() = prefs.getBoolean("SOUND_ENABLED", false)
        set(value) = prefs.edit().putBoolean("SOUND_ENABLED", value).apply()

    // القيمة الافتراضية الآن هي false (مغلق)
    var isVibrationEnabled: Boolean
        get() = prefs.getBoolean("VIBRATION_ENABLED", false)
        set(value) = prefs.edit().putBoolean("VIBRATION_ENABLED", value).apply()
}
