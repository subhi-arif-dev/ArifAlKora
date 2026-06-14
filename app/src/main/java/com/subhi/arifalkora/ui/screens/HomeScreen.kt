package com.subhi.arifalkora.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.subhi.arifalkora.R
import com.subhi.arifalkora.ui.theme.*

@Composable
fun HomeScreen(
    onLevelSelected: (String) -> Unit, 
    onSettingsClick: () -> Unit,
    onExitApp: () -> Unit // تمت إضافة أمر الخروج النهائي
) {
    var showExitDialog by remember { mutableStateOf(false) }

    // التقاط ضغطة الرجوع في الشاشة الرئيسية
    BackHandler {
        showExitDialog = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.icon_settings),
            contentDescription = "Settings",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp)
                .size(36.dp)
                .clickable { onSettingsClick() }
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(160.dp),
                contentScale = ContentScale.Fit
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Surface(
                color = GlassBackground,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(bottom = 36.dp)
            ) {
                Text(
                    text = "هل أنت مستعد للتحدي؟",
                    color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                )
            }

            LevelButton("المستوى العادي 🟢", RoyalGreen) { onLevelSelected("easy_questions.json") }
            LevelButton("المستوى المتوسط 🟡", Color(0xFFB8860B)) { onLevelSelected("medium_questions.json") }
            LevelButton("المستوى الصعب 🟠", Color(0xFFD35400)) { onLevelSelected("hard_questions.json") }
            LevelButton("مستوى الأساطير 🔴", ErrorRed) { onLevelSelected("legendary_questions.json") }
        }

        // تصميم رسالة تأكيد الخروج من التطبيق
        if (showExitDialog) {
            AlertDialog(
                onDismissRequest = { showExitDialog = false },
                containerColor = DarkBackground,
                titleContentColor = GoldAccent,
                textContentColor = Color.White,
                title = { Text("الخروج من التطبيق", fontWeight = FontWeight.Bold) },
                text = { Text("هل أنت متأكد أنك تريد مغادرة تحدي عارف الكورة؟", fontSize = 16.sp) },
                confirmButton = {
                    Button(
                        onClick = onExitApp, // الخروج النهائي
                        colors = ButtonDefaults.buttonColors(containerColor = ErrorRed)
                    ) {
                        Text("نعم، خروج", color = Color.White)
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = { showExitDialog = false },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Text("البقاء")
                    }
                }
            )
        }
    }
}

@Composable
fun LevelButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color.copy(alpha = 0.9f)),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth(0.85f).padding(vertical = 8.dp).height(65.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
    ) {
        Text(text = text, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}
