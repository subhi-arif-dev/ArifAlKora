package com.subhi.arifalkora.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.subhi.arifalkora.data.model.Question
import com.subhi.arifalkora.data.repository.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel(private val repository: QuestionRepository) : ViewModel() {

    // قائمة الأسئلة التي سيتم تحميلها
    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()

    // مؤشر (رقم) السؤال الحالي
    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    // رصيد نقاط اللاعب
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    // دالة لتحميل مستوى معين (مثلاً نمرر لها "easy_questions.json")
    fun loadLevel(fileName: String) {
        val loadedQuestions = repository.loadQuestionsByLevel(fileName)
        _questions.value = loadedQuestions
        _currentQuestionIndex.value = 0
        _score.value = 0
    }

    // دالة للحصول على السؤال الحالي وعرضه على الشاشة
    fun getCurrentQuestion(): Question? {
        if (_questions.value.isEmpty()) return null
        return _questions.value[_currentQuestionIndex.value]
    }

    // دالة للانتقال للسؤال التالي وحساب النقاط
    fun answerQuestion(isCorrect: Boolean) {
        if (isCorrect) {
            _score.value += 10 // إضافة 10 نقاط إذا كانت الإجابة صحيحة
        }
        
        // إذا لم نصل لنهاية الأسئلة، انتقل للسؤال التالي
        if (_currentQuestionIndex.value < _questions.value.size - 1) {
            _currentQuestionIndex.value += 1
        } else {
            // هنا يمكننا لاحقاً إضافة حالة لإنهاء اللعبة وإظهار النتيجة النهائية
        }
    }
}
