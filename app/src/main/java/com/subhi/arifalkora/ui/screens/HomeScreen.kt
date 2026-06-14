package com.subhi.arifalkora.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.subhi.arifalkora.R // استدعاء مجلد الصور الخاص بنا
import com.subhi.arifalkora.ui.theme.*

@Composable
fun HomeScreen(onLevelSelected: (String) -> Unit) {
    // 1. حاوية Box الأساسية: تسمح لنا بوضع العناصر فوق بعضها (طبقات)
    Box(modifier = Modifier.fillMaxSize()) {
        
        // 2. طبقة الخلفية (صورة الملعب)
        Image(
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // يقوم بقص الأطراف الزائدة بذكاء لملء الشاشة بالكامل دون تمطيط
        )

        // 3. طبقة أيقونة الإعدادات (الترس) في الزاوية العلوية
        Image(
            painter = painterResource(id = R.drawable.icon_settings),
            contentDescription = "Settings",
            modifier = Modifier
                .align(Alignment.TopEnd) // المحاذاة لأعلى اليمين
                .padding(24.dp) // إبعادها عن الحافة لتكون مريحة للإصبع
                .size(36.dp) // حجم احترافي للأيقونات
                .clickable { /* سنضيف وظيفة فتح الإعدادات لاحقاً */ }
        )

        // 4. طبقة المحتوى الرئيسي (اللوجو + الأزرار)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // الشعار الملوكي الخاص بك
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(160.dp), // حجم متناسق ليكون هو البطل في الشاشة
                contentScale = ContentScale.Fit
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // حاوية زجاجية خلف النص لضمان وضوحه فوق صورة الملعب
            Surface(
                color = GlassBackground,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(bottom = 36.dp)
            ) {
                Text(
                    text = "هل أنت مستعد للتحدي؟",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                )
            }

            // أزرار المستويات (تم إضافة نسبة شفافية بسيطة 90% لتندمج مع الملعب)
            LevelButton("المستوى العادي 🟢", RoyalGreen) { onLevelSelected("easy_questions.json") }
            LevelButton("المستوى المتوسط 🟡", Color(0xFFB8860B)) { onLevelSelected("medium_questions.json") }
            LevelButton("المستوى الصعب 🟠", Color(0xFFD35400)) { onLevelSelected("hard_questions.json") }
            LevelButton("مستوى الأساطير 🔴", ErrorRed) { onLevelSelected("legendary_questions.json") }
        }
    }
}

// تصميم الزر الموحد
@Composable
fun LevelButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color.copy(alpha = 0.9f)), // 0.9 تعني شفافية 10%
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(vertical = 8.dp)
            .height(65.dp), // ارتفاع مريح للضغط
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp) // إضافة ظل خفيف أسفل الزر ليعطيه بروزاً
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
