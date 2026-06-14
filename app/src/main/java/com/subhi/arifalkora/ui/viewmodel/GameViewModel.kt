package com.subhi.arifalkora.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.subhi.arifalkora.data.model.Question
import com.subhi.arifalkora.data.repository.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel(private val repository: QuestionRepository) : ViewModel() {

    // متغير لتحديد ما إذا كان اللاعب في شاشة البداية أم داخل اللعبة
    private val _isGameActive = MutableStateFlow(false)
    val isGameActive: StateFlow<Boolean> = _isGameActive.asStateFlow()

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    // بمجرد اختيار المستوى، يتم تحميل الأسئلة ونقل اللاعب للعبة
    fun loadLevel(fileName: String) {
        val loadedQuestions = repository.loadQuestionsByLevel(fileName)
        if (loadedQuestions.isNotEmpty()) {
            _questions.value = loadedQuestions
            _currentQuestionIndex.value = 0
            _score.value = 0
            _isGameActive.value = true // إعطاء إشارة البدء
        }
    }

    fun answerQuestion(isCorrect: Boolean) {
        if (isCorrect) {
            _score.value += 10
        }
        
        if (_currentQuestionIndex.value < _questions.value.size - 1) {
            _currentQuestionIndex.value += 1
        } else {
            // مؤقتاً: عند انتهاء الأسئلة، نعيده للشاشة الرئيسية
            _isGameActive.value = false 
        }
    }
}
