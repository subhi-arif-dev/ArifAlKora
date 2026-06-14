package com.subhi.arifalkora.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.subhi.arifalkora.R
import com.subhi.arifalkora.ui.theme.*

@Composable
fun ResultScreen(
    score: Int,
    totalQuestions: Int,
    onPlayAgain: () -> Unit,
    onReturnHome: () -> Unit
) {
    val maxScore = totalQuestions * 10
    val percentage = (score.toFloat() / maxScore) * 100

    val (title, titleColor) = when {
        percentage == 100f -> Pair("أسطورة كرة القدم 👑", GoldAccent)
        percentage >= 80f -> Pair("سينيور كروي محنك 🧠", RoyalGreen)
        percentage >= 50f -> Pair("مشجع درجة أولى ⚽", Color.White)
        else -> Pair("تحتاج لمتابعة المباريات أكثر 📺", ErrorRed)
    }

    // الطبقة الأساسية (الملعب)
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            
            // عرض الكأس الذهبي فقط إذا كانت النتيجة 80% أو أعلى (لمسة السينيور)
            if (percentage >= 80f) {
                Image(
                    painter = painterResource(id = R.drawable.gold_trophy),
                    contentDescription = "Gold Trophy",
                    modifier = Modifier
                        .size(180.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Fit
                )
            } else {
                 Text(
                    text = "نهاية التحدي!",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            // بطاقة النتيجة الزجاجية
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
                        fontSize = 72.sp, // كبرنا حجم النتيجة لتكون أوضح
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
                        color = titleColor,
                        fontSize = 24.sp,
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
                    .height(60.dp),
                shape = RoundedCornerShape(14.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Text("إعادة التحدي 🔄", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onReturnHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White,
                    containerColor = GlassBackground // خلفية زجاجية خفيفة للزر
                )
            ) {
                Text("العودة للقائمة الرئيسية 🏠", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
