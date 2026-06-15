package com.subhi.arifalkora.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.subhi.arifalkora.R
import com.subhi.arifalkora.ui.theme.*

@Composable
fun HintCard(hintText: String, onHintClick: () -> Unit) {
    var isHintVisible by remember { mutableStateOf(false) }
    var isPointDeducted by remember { mutableStateOf(false) } // لمنع الخصم مرتين لنفس السؤال

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { 
                isHintVisible = !isHintVisible 
                // خصم النقاط مرة واحدة فقط عند إظهار التلميح لأول مرة
                if (isHintVisible && !isPointDeducted) {
                    onHintClick()
                    isPointDeducted = true
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = RoyalGreen),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.icon_hint),
                    contentDescription = "VAR Hint",
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = if (isHintVisible) "إخفاء التلميح 🙈" else "مساعدة الـ VAR (خصم 5 نقاط)",
                    color = GoldAccent,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        AnimatedVisibility(
            visible = isHintVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(GlassBackground, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "تلميح: $hintText",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}
