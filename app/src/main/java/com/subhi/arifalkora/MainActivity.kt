package com.subhi.arifalkora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.subhi.arifalkora.data.repository.QuestionRepository
import com.subhi.arifalkora.data.repository.SettingsManager
import com.subhi.arifalkora.ui.screens.*
import com.subhi.arifalkora.ui.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // تعريف المستودع ومدير الذاكرة وتمريرهما للمدير الفني
        val repository = QuestionRepository(this)
        val settingsManager = SettingsManager(this)
        val viewModel = GameViewModel(repository, settingsManager)

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

                    // خريطة التنقل الخماسية
                    if (showSplash) {
                        SplashScreen(onSplashFinished = { showSplash = false })
                    } else {
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
                                    onBackClick = { viewModel.returnHome() }
                                )
                            }
                            else -> {
                                HomeScreen(
                                    onLevelSelected = { fileName -> viewModel.loadLevel(fileName) },
                                    onSettingsClick = { viewModel.openSettings() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
