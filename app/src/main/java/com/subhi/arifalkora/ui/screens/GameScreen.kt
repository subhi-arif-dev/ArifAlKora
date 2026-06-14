package com.subhi.arifalkora.ui.screens
// ... (نفس الـ imports السابقة) ...
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.subhi.arifalkora.R
import com.subhi.arifalkora.data.model.Question
import com.subhi.arifalkora.ui.theme.*
import com.subhi.arifalkora.ui.components.HintCard
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    question: Question,
    onNextQuestion: (Boolean) -> Unit,
    onBackClick: () -> Unit // تمت إضافة أمر الرجوع هنا!
) {
    var selectedOptionIndex by remember(question.id) { mutableStateOf<Int?>(null) }

    LaunchedEffect(selectedOptionIndex) {
        if (selectedOptionIndex != null) {
            val isCorrect = selectedOptionIndex == question.correct_index
            delay(2000)
            onNextQuestion(isCorrect)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 1. إضافة زر الرجوع في الزاوية العلوية
        TextButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 48.dp, start = 16.dp)
        ) {
            Text("🔙 خروج", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(56.dp)) // نزلنا المحتوى قليلاً لترك مساحة لزر الرجوع
            
            Text(
                text = "المستوى: ${question.level.uppercase()}",
                color = GoldAccent, fontSize = 20.sp, fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = GlassBackground)
            ) {
                Text(
                    text = question.question, color = Color.White, fontSize = 22.sp,
                    fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
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
            HintCard(hintText = question.hint)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
