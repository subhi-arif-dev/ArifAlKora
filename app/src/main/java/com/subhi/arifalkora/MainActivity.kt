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

        // 1. تهيئة المحرك (المستودع) والمدير الفني بمجرد فتح التطبيق
        val repository = QuestionRepository(this)
        val viewModel = GameViewModel(repository)

        // 2. أمر التحميل: سنقوم بتحميل مستوى "الأسئلة السهلة" كبداية تلقائية
        viewModel.loadLevel("easy_questions.json")

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 3. مراقبة تدفق البيانات من المدير الفني
                    val questions by viewModel.questions.collectAsState()
                    val currentIndex by viewModel.currentQuestionIndex.collectAsState()

                    // 4. إذا تم تحميل الأسئلة بنجاح، قم بعرض الشاشة واسحب السؤال الحالي
                    if (questions.isNotEmpty()) {
                        GameScreen(question = questions[currentIndex])
                    }
                }
            }
        }
    }
}
