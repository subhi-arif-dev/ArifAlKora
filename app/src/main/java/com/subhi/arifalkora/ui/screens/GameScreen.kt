package com.subhi.arifalkora.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.subhi.arifalkora.data.model.Question
import com.subhi.arifalkora.ui.theme.*
import com.subhi.arifalkora.ui.components.HintCard

@Composable
fun GameScreen(question: Question) {
    // متغير لتخزين إجابة اللاعب (لتحديد ما إذا كان قد أجاب أم لا)
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. رأس الشاشة (مستوى الصعوبة)
        Text(
            text = "المستوى: ${question.level.uppercase()}",
            color = GoldAccent,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(24.dp))

        // 2. بطاقة السؤال (تصميم زجاجي)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = GlassBackground)
        ) {
            Text(
                text = question.question,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 3. أزرار الخيارات الـ 4
        question.options.forEachIndexed { index, optionText ->
            val isSelected = selectedOptionIndex == index
            val isCorrect = index == question.correct_index
            
            // تحديد لون الزر بذكاء بعد إجابة المستخدم
            val buttonColor = if (selectedOptionIndex != null) {
                when {
                    isCorrect -> RoyalGreen // الإجابة الصحيحة تضيء بالأخضر دائماً
                    isSelected && !isCorrect -> ErrorRed // إجابة المستخدم الخاطئة تضيء بالأحمر
                    else -> GlassBackground // باقي الأزرار تبقى شفافة
                }
            } else {
                GlassBackground // قبل الإجابة، كل الأزرار شفافة
            }

            Button(
                onClick = { 
                    // يمنع اللاعب من تغيير إجابته بعد الضغط!
                    if (selectedOptionIndex == null) { 
                        selectedOptionIndex = index 
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(56.dp)
            ) {
                Text(
                    text = optionText,
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // لدفع زر التلميح لأسفل الشاشة

        // 4. استدعاء زر مساعدة الـ VAR (التلميح المخفي)
        HintCard(hintText = question.hint)
    }
}
