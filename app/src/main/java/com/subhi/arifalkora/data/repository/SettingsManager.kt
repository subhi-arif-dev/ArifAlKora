package com.subhi.arifalkora.data.repository

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {
    // إنشاء ملف ذاكرة صغير ومخفي داخل الهاتف اسمه ArifAlKoraSettings
    private val prefs: SharedPreferences = context.getSharedPreferences("ArifAlKoraSettings", Context.MODE_PRIVATE)

    // مفتاح حفظ حالة الصوت (الافتراضي: مفعل true)
    var isSoundEnabled: Boolean
        get() = prefs.getBoolean("SOUND_ENABLED", true)
        set(value) = prefs.edit().putBoolean("SOUND_ENABLED", value).apply()

    // مفتاح حفظ حالة الاهتزاز (الافتراضي: مفعل true)
    var isVibrationEnabled: Boolean
        get() = prefs.getBoolean("VIBRATION_ENABLED", true)
        set(value) = prefs.edit().putBoolean("VIBRATION_ENABLED", value).apply()
}
