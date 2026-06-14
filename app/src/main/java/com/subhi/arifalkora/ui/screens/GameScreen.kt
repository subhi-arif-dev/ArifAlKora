package com.subhi.arifalkora.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.subhi.arifalkora.data.model.Question
import com.subhi.arifalkora.ui.theme.*
import com.subhi.arifalkora.ui.components.HintCard
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    question: Question,
    onNextQuestion: (Boolean) -> Unit
) {
    var selectedOptionIndex by remember(question.id) { mutableStateOf<Int?>(null) }

    LaunchedEffect(selectedOptionIndex) {
        if (selectedOptionIndex != null) {
            val isCorrect = selectedOptionIndex == question.correct_index
            delay(2000)
            onNextQuestion(isCorrect)
        }
    }

    // الطبقة الأساسية (الملعب)
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            
            Text(
                text = "المستوى: ${question.level.uppercase()}",
                color = GoldAccent,
                fontSize = 20.sp,
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
                
                val buttonColor = if (selectedOptionIndex != null) {
                    if (isCorrectAnswer) RoyalGreen else if (isSelected) ErrorRed else GlassBackground
                } else GlassBackground

                Button(
                    onClick = { if (selectedOptionIndex == null) selectedOptionIndex = index },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor.copy(alpha = 0.8f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).height(56.dp)
                ) {
                    Text(text = optionText, color = Color.White, fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            
            // هنا استخدمنا الـ HintCard الملوكي (سنعدل داخله ليستخدم أيقونة الصافرة)
            HintCard(hintText = question.hint)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
