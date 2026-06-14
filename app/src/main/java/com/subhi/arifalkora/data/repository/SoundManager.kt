package com.subhi.arifalkora.data.repository

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.subhi.arifalkora.R

class SoundManager(
    private val context: Context,
    private val settingsManager: SettingsManager
) {
    fun playCorrectSound() {
        if (settingsManager.isSoundEnabled) {
            try {
                val player = MediaPlayer.create(context, R.raw.correct)
                player.setOnCompletionListener { it.release() } // تنظيف الذاكرة بعد انتهاء الصوت
                player.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun playWrongSound() {
        if (settingsManager.isSoundEnabled) {
            try {
                val player = MediaPlayer.create(context, R.raw.wrong)
                player.setOnCompletionListener { it.release() }
                player.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        vibrate() // تشغيل الاهتزاز مع الإجابة الخاطئة
    }

    fun playWinSound() {
        if (settingsManager.isSoundEnabled) {
            try {
                val player = MediaPlayer.create(context, R.raw.win)
                player.setOnCompletionListener { it.release() }
                player.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun vibrate() {
        if (settingsManager.isVibrationEnabled) {
            try {
                val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                    vibratorManager.defaultVibrator
                } else {
                    @Suppress("DEPRECATION")
                    context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                }

                // اهتزاز لمدة 300 ملي ثانية
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(300)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
