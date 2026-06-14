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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.subhi.arifalkora.ui.theme.*

@Composable
fun ResultScreen(
    score: Int,
    totalQuestions: Int,
    onPlayAgain: () -> Unit,
    onReturnHome: () -> Unit
) {
    // حساب النسبة المئوية للنجاح
    val maxScore = totalQuestions * 10
    val percentage = (score.toFloat() / maxScore) * 100

    // تحديد اللقب الملوكي بناءً على النسبة
    val (title, color) = when {
        percentage == 100f -> Pair("أسطورة كرة القدم 👑", GoldAccent)
        percentage >= 80f -> Pair("سينيور كروي محنك 🧠", RoyalGreen)
        percentage >= 50f -> Pair("مشجع درجة أولى ⚽", Color.White)
        else -> Pair("تحتاج لمتابعة المباريات أكثر 📺", ErrorRed)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "نهاية التحدي!",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // بطاقة النتيجة
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = GlassBackground)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$score",
                    color = GoldAccent,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "من $maxScore نقطة",
                    color = Color.LightGray,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(24.dp))
                Divider(color = Color.Gray.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = title,
                    color = color,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // أزرار التحكم
        Button(
            onClick = onPlayAgain,
            colors = ButtonDefaults.buttonColors(containerColor = RoyalGreen),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("إعادة المستوى 🔄", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onReturnHome,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
        ) {
            Text("العودة للقائمة الرئيسية 🏠", fontSize = 18.sp)
        }
    }
}
