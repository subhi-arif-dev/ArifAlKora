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
import com.subhi.arifalkora.ui.screens.GameScreen
import com.subhi.arifalkora.ui.screens.HomeScreen
import com.subhi.arifalkora.ui.screens.ResultScreen
import com.subhi.arifalkora.ui.screens.SplashScreen
import com.subhi.arifalkora.ui.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = QuestionRepository(this)
        val viewModel = GameViewModel(repository)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // متغير لتتبع هل نحن في شاشة البداية أم انتهت؟
                    var showSplash by remember { mutableStateOf(true) }

                    val isGameActive by viewModel.isGameActive.collectAsState()
                    val isGameOver by viewModel.isGameOver.collectAsState()
                    val questions by viewModel.questions.collectAsState()
                    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
                    val score by viewModel.score.collectAsState()

                    // هندسة التنقل الرباعية (Splash -> Home -> Game -> Result)
                    if (showSplash) {
                        SplashScreen(
                            onSplashFinished = { showSplash = false }
                        )
                    } else {
                        when {
                            isGameOver -> {
                                ResultScreen(
                                    score = score,
                                    totalQuestions = questions.size,
                                    onPlayAgain = { viewModel.replayLevel() },
                                    onReturnHome = { viewModel.returnHome() }
                                )
                            }
                            isGameActive && questions.isNotEmpty() -> {
                                GameScreen(
                                    question = questions[currentIndex],
                                    onNextQuestion = { isCorrect ->
                                        viewModel.answerQuestion(isCorrect)
                                    }
                                )
                            }
                            else -> {
                                HomeScreen(
                                    onLevelSelected = { fileName ->
                                        viewModel.loadLevel(fileName)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
