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
import com.subhi.arifalkora.ui.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = QuestionRepository(this)
        val viewModel = GameViewModel(repository)

        viewModel.loadLevel("easy_questions.json")

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val questions by viewModel.questions.collectAsState()
                    val currentIndex by viewModel.currentQuestionIndex.collectAsState()

                    if (questions.isNotEmpty()) {
                        GameScreen(
                            question = questions[currentIndex],
                            onNextQuestion = { isCorrect ->
                                // بمجرد مرور الثانيتين في شاشة اللعب، يتم استدعاء هذا الأمر
                                viewModel.answerQuestion(isCorrect)
                            }
                        )
                    }
                }
            }
        }
    }
}
