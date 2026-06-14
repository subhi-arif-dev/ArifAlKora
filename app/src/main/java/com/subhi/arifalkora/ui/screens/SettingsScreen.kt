package com.subhi.arifalkora.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.subhi.arifalkora.R
import com.subhi.arifalkora.ui.theme.*

@Composable
fun SettingsScreen(
    isSoundEnabled: Boolean,
    isVibrationEnabled: Boolean,
    onSoundToggled: (Boolean) -> Unit,
    onVibrationToggled: (Boolean) -> Unit,
    onBackClick: () -> Unit
) {
    // التقاط ضغطة زر الرجوع في الهاتف وإعادتنا للرئيسية
    BackHandler {
        onBackClick()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        TextButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.TopStart).padding(top = 48.dp, start = 16.dp)
        ) {
            Text("🔙 رجوع", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_settings),
                contentDescription = "Settings",
                modifier = Modifier.size(90.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("الإعدادات", color = GoldAccent, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            
            Spacer(modifier = Modifier.height(48.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = GlassBackground)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    SettingRow("المؤثرات الصوتية 🎵", isSoundEnabled, onSoundToggled)
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(color = Color.Gray.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(16.dp))
                    SettingRow("الاهتزاز عند الخطأ 📳", isVibrationEnabled, onVibrationToggled)
                }
            }
        }
    }
}

@Composable
fun SettingRow(title: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = GoldAccent,
                checkedTrackColor = RoyalGreen,
                uncheckedThumbColor = Color.LightGray,
                uncheckedTrackColor = Color.DarkGray
            )
        )
    }
}
