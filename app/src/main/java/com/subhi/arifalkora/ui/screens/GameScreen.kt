package com.subhi.arifalkora.ui.screens

import androidx.activity.compose.BackHandler
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
    onAnswerSelected: (Boolean) -> Unit, // دالة الصوت الفوري
    onNextQuestion: () -> Unit,          // دالة الانتقال
    onHintUsed: () -> Unit,
    onBackClick: () -> Unit
) {
    var selectedOptionIndex by remember(question.id) { mutableStateOf<Int?>(null) }
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler {
        showExitDialog = true
    }

    LaunchedEffect(selectedOptionIndex) {
        if (selectedOptionIndex != null) {
            val isCorrect = selectedOptionIndex == question.correct_index
            onAnswerSelected(isCorrect) // 1. تشغيل الصوت ورفع النقاط فوراً
            delay(2000)                 // 2. انتظار ثانيتين للمشاهدة
            onNextQuestion()            // 3. الانتقال للسؤال التالي
        }
    }

    val arabicLevelName = when (question.level.lowercase()) {
        "easy" -> "العادي 🟢"
        "medium" -> "المتوسط 🟡"
        "hard" -> "الصعب 🟠"
        "legendary" -> "الأساطير 🔴"
        else -> question.level
    }

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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(90.dp))
            
            Surface(
                color = Color.Black.copy(alpha = 0.6f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, GoldAccent)
            ) {
                Text(
                    text = "المستوى: $arabicLevelName",
                    color = GoldAccent, fontSize = 20.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.75f)),
                border = BorderStroke(1.5.dp, RoyalGreen)
            ) {
                Text(
                    text = question.question, color = Color.White, fontSize = 24.sp, 
                    fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
                    modifier = Modifier.padding(24.dp), lineHeight = 34.sp
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))

            question.options.forEachIndexed { index, optionText ->
                val isCorrectAnswer = index == question.correct_index
                val isSelected = selectedOptionIndex == index
                
                val buttonColor = if (selectedOptionIndex != null) {
                    if (isCorrectAnswer) RoyalGreen else if (isSelected) ErrorRed else Color.DarkGray.copy(alpha = 0.9f)
                } else Color.White.copy(alpha = 0.15f)

                Button(
                    onClick = { if (selectedOptionIndex == null) selectedOptionIndex = index },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).height(60.dp)
                ) {
                    Text(text = optionText, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            key(question.id) {
                HintCard(
                    hintText = question.hint,
                    onHintClick = onHintUsed
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }

        TextButton(
            onClick = { showExitDialog = true },
            modifier = Modifier.align(Alignment.TopStart).padding(top = 32.dp, start = 8.dp)
        ) {
            Text("🔙 خروج", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        if (showExitDialog) {
            AlertDialog(
                onDismissRequest = { showExitDialog = false },
                containerColor = DarkBackground,
                titleContentColor = GoldAccent,
                textContentColor = Color.White,
                title = { Text("الانسحاب من التحدي", fontWeight = FontWeight.Bold) },
                text = { Text("هل أنت متأكد أنك تريد الانسحاب والعودة للقائمة الرئيسية؟ ستفقد تقدمك في هذا المستوى.", fontSize = 16.sp) },
                confirmButton = {
                    Button(
                        onClick = {
                            showExitDialog = false
                            onBackClick()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ErrorRed)
                    ) {
                        Text("نعم، انسحب", color = Color.White)
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = { showExitDialog = false },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Text("إلغاء")
                    }
                }
            )
        }
    }
}
