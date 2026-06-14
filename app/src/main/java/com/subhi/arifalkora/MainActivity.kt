package com.subhi.arifalkora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.subhi.arifalkora.data.repository.QuestionRepository
import com.subhi.arifalkora.ui.screens.GameScreen
import com.subhi.arifalkora.ui.screens.HomeScreen
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
                    val isGameActive by viewModel.isGameActive.collectAsState()
                    val questions by viewModel.questions.collectAsState()
                    val currentIndex by viewModel.currentQuestionIndex.collectAsState()

                    // هندسة التنقل بين الشاشات
                    if (!isGameActive) {
                        // إذا اللعبة غير نشطة -> اعرض شاشة المستويات
                        HomeScreen(
                            onLevelSelected = { fileName ->
                                viewModel.loadLevel(fileName)
                            }
                        )
                    } else {
                        // إذا اللعبة نشطة وتم تحميل الأسئلة -> اعرض اللعبة
                        if (questions.isNotEmpty()) {
                            GameScreen(
                                question = questions[currentIndex],
                                onNextQuestion = { isCorrect ->
                                    viewModel.answerQuestion(isCorrect)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
