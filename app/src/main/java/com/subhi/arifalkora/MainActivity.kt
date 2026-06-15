package com.subhi.arifalkora

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.gms.ads.MobileAds
import com.subhi.arifalkora.data.repository.NotificationWorker
import com.subhi.arifalkora.data.repository.QuestionRepository
import com.subhi.arifalkora.data.repository.SettingsManager
import com.subhi.arifalkora.data.repository.SoundManager
import com.subhi.arifalkora.ui.components.BannerAd
import com.subhi.arifalkora.ui.screens.*
import com.subhi.arifalkora.ui.viewmodel.GameViewModel
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this) {}

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }

        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(1, TimeUnit.DAYS)
            .build()
        
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DailyFootballNotification",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

        val repository = QuestionRepository(this)
        val settingsManager = SettingsManager(this)
        val soundManager = SoundManager(this, settingsManager)
        val viewModel = GameViewModel(repository, settingsManager, soundManager)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    var showSplash by remember { mutableStateOf(true) }

                    val isGameActive by viewModel.isGameActive.collectAsState()
                    val isGameOver by viewModel.isGameOver.collectAsState()
                    val isSettingsActive by viewModel.isSettingsActive.collectAsState()
                    
                    val questions by viewModel.questions.collectAsState()
                    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
                    val score by viewModel.score.collectAsState()
                    
                    val isSoundEnabled by viewModel.isSoundEnabled.collectAsState()
                    val isVibrationEnabled by viewModel.isVibrationEnabled.collectAsState()

                    if (showSplash) {
                        SplashScreen(onSplashFinished = { showSplash = false })
                    } else {
                        Box(modifier = Modifier.fillMaxSize()) {
                            
                            Box(modifier = Modifier.fillMaxSize().padding(bottom = 50.dp)) {
                                when {
                                    isSettingsActive -> {
                                        SettingsScreen(
                                            isSoundEnabled = isSoundEnabled,
                                            isVibrationEnabled = isVibrationEnabled,
                                            onSoundToggled = { viewModel.toggleSound(it) },
                                            onVibrationToggled = { viewModel.toggleVibration(it) },
                                            onBackClick = { viewModel.closeSettings() }
                                        )
                                    }
                                    isGameOver -> {
                                        ResultScreen(
                                            score = score, totalQuestions = questions.size,
                                            onPlayAgain = { viewModel.replayLevel() },
                                            onReturnHome = { viewModel.returnHome() }
                                        )
                                    }
                                    isGameActive && questions.isNotEmpty() -> {
                                        GameScreen(
                                            question = questions[currentIndex],
                                            onNextQuestion = { isCorrect -> viewModel.answerQuestion(isCorrect) },
                                            onHintUsed = { viewModel.useHint() }, // تفعيل الخصم هنا
                                            onBackClick = { viewModel.returnHome() }
                                        )
                                    }
                                    else -> {
                                        HomeScreen(
                                            onLevelSelected = { fileName -> viewModel.loadLevel(fileName) },
                                            onSettingsClick = { viewModel.openSettings() },
                                            onExitApp = { finish() }
                                        )
                                    }
                                }
                            }

                            BannerAd(modifier = Modifier.align(Alignment.BottomCenter))
                        }
                    }
                }
            }
        }
    }
}
