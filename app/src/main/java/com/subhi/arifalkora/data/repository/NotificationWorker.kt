package com.subhi.arifalkora.data.repository

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.subhi.arifalkora.MainActivity
import com.subhi.arifalkora.R
import kotlin.random.Random

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        showNotification()
        return Result.success()
    }

    private fun showNotification() {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "arif_alkora_daily_channel"

        // إنشاء قناة الإشعارات لهواتف أندرويد 8 وما فوق
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "تحديات عارف الكورة",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // أمر فتح التطبيق عند الضغط على الإشعار
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // قائمة الرسائل العشوائية لإنعاش الذاكرة الكروية
        val messages = listOf(
            "هل أنت مستعد لتحدي كروي جديد اليوم؟ ⚽",
            "أنعش ذاكرتك الكروية، وشارك في التحدي! 🏆",
            "الـ VAR بانتظارك.. هل تستطيع تجاوز مستوى الأساطير؟ 🔥",
            "معلومات كروية جديدة بانتظارك، افتح التطبيق الآن! 🧠",
            "حان وقت اختبار معلوماتك.. هل أنت سينيور كروي محنك؟ 👑"
        )
        val randomMessage = messages[Random.nextInt(messages.size)]

        // بناء الإشعار
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.app_logo)
            .setContentTitle("عارف الكورة")
            .setContentText(randomMessage)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // اختفاء الإشعار بعد الضغط عليه
            .build()

        notificationManager.notify(1, notification)
    }
}
