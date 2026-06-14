package com.subhi.arifalkora.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.subhi.arifalkora.ui.theme.*

@Composable
fun HomeScreen(onLevelSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🏆 عارف الكورة 🏆",
            color = GoldAccent,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "هل أنت مستعد للتحدي؟",
            color = Color.White,
            fontSize = 18.sp
        )
        
        Spacer(modifier = Modifier.height(48.dp))

        // أزرار المستويات التي تمرر اسم ملف الـ JSON الصحيح عند الضغط
        LevelButton("المستوى العادي 🟢", RoyalGreen) { onLevelSelected("easy_questions.json") }
        LevelButton("المستوى المتوسط 🟡", Color(0xFFB8860B)) { onLevelSelected("medium_questions.json") }
        LevelButton("المستوى الصعب 🟠", Color(0xFFD35400)) { onLevelSelected("hard_questions.json") }
        LevelButton("مستوى الأساطير 🔴", ErrorRed) { onLevelSelected("legendary_questions.json") }
    }
}

// دالة مخصصة لرسم الأزرار بشكل موحد وأنيق
@Composable
fun LevelButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(vertical = 10.dp)
            .height(65.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
