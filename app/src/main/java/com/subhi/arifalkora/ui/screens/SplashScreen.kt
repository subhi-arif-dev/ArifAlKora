package com.subhi.arifalkora.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.subhi.arifalkora.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    // هذا الكود يعمل مثل "المؤقت" (Timer)
    LaunchedEffect(key1 = true) {
        delay(3000) // الانتظار لمدة 3 ثوانٍ (3000 ملي ثانية)
        onSplashFinished() // إعطاء أمر الانتقال للشاشة الرئيسية
    }

    // عرض صورة الافتتاحية بكامل الشاشة
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.splash_screen),
            contentDescription = "Splash Screen",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
