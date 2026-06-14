package com.subhi.arifalkora.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.subhi.arifalkora.data.model.Question
import com.subhi.arifalkora.data.repository.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel(private val repository: QuestionRepository) : ViewModel() {

    // الحالات (States) التي تتحكم في أي شاشة سيتم عرضها
    private val _isGameActive = MutableStateFlow(false)
    val isGameActive: StateFlow<Boolean> = _isGameActive.asStateFlow()

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver: StateFlow<Boolean> = _isGameOver.asStateFlow()

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    // حفظ اسم المستوى الحالي لإمكانية إعادة اللعب
    private var currentLevelFileName: String = ""

    fun loadLevel(fileName: String) {
        val loadedQuestions = repository.loadQuestionsByLevel(fileName)
        if (loadedQuestions.isNotEmpty()) {
            currentLevelFileName = fileName
            _questions.value = loadedQuestions
            _currentQuestionIndex.value = 0
            _score.value = 0
            _isGameActive.value = true
            _isGameOver.value = false // التأكد من إيقاف شاشة النتيجة
        }
    }

    fun answerQuestion(isCorrect: Boolean) {
        if (isCorrect) {
            _score.value += 10
        }
        
        if (_currentQuestionIndex.value < _questions.value.size - 1) {
            _currentQuestionIndex.value += 1
        } else {
            // اللعبة انتهت!
            _isGameActive.value = false
            _isGameOver.value = true // تفعيل شاشة النتيجة
        }
    }

    // دالة للعودة للرئيسية
    fun returnHome() {
        _isGameOver.value = false
        _isGameActive.value = false
        _questions.value = emptyList()
    }

    // دالة لإعادة نفس المستوى
    fun replayLevel() {
        if (currentLevelFileName.isNotEmpty()) {
            loadLevel(currentLevelFileName)
        }
    }
}
