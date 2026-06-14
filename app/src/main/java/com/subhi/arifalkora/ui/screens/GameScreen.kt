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
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    question: Question,
    onNextQuestion: (Boolean) -> Unit // دالة نمررها للانتقال للسؤال التالي بعد الإجابة
) {
    // استخدمنا (question.id) لكي يتم تصفير الخيار عند الانتقال لسؤال جديد
    var selectedOptionIndex by remember(question.id) { mutableStateOf<Int?>(null) }

    // هذا الـ Effect ينتظر ثانيتين بعد إجابة اللاعب ثم يرسل الأمر بالانتقال للسؤال التالي
    LaunchedEffect(selectedOptionIndex) {
        if (selectedOptionIndex != null) {
            val isCorrect = selectedOptionIndex == question.correct_index
            // ملاحظة السينيور: هنا سنضع كود تشغيل الصوت لاحقاً!
            delay(2000) // انتظار ثانيتين لكي يرى اللاعب الإجابة الصحيحة
            onNextQuestion(isCorrect)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "المستوى: ${question.level.uppercase()}",
            color = GoldAccent,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(24.dp))

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

        question.options.forEachIndexed { index, optionText ->
            val isCorrectAnswer = index == question.correct_index
            val isSelected = selectedOptionIndex == index
            
            // هندسة الألوان كما طلبت بالضبط:
            val buttonColor = if (selectedOptionIndex != null) {
                when {
                    isCorrectAnswer -> RoyalGreen // الإجابة الصحيحة تضيء بالأخضر دائماً سواء اختارها أم لا
                    isSelected && !isCorrectAnswer -> ErrorRed // إذا اختار إجابة خاطئة تضيء بالأحمر
                    else -> GlassBackground // باقي الأزرار تبقى شفافة
                }
            } else {
                GlassBackground // قبل الإجابة كل الأزرار شفافة
            }

            Button(
                onClick = { 
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

        Spacer(modifier = Modifier.weight(1f))
        HintCard(hintText = question.hint)
    }
}
