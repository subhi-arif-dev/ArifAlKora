package com.subhi.arifalkora.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.subhi.arifalkora.data.model.Question
import com.subhi.arifalkora.data.repository.QuestionRepository
import com.subhi.arifalkora.data.repository.SettingsManager
import com.subhi.arifalkora.data.repository.SoundManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel(
    private val repository: QuestionRepository,
    private val settingsManager: SettingsManager,
    private val soundManager: SoundManager
) : ViewModel() {

    private val _isGameActive = MutableStateFlow(false)
    val isGameActive: StateFlow<Boolean> = _isGameActive.asStateFlow()

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver: StateFlow<Boolean> = _isGameOver.asStateFlow()

    private val _isSettingsActive = MutableStateFlow(false)
    val isSettingsActive: StateFlow<Boolean> = _isSettingsActive.asStateFlow()

    private val _isSoundEnabled = MutableStateFlow(settingsManager.isSoundEnabled)
    val isSoundEnabled: StateFlow<Boolean> = _isSoundEnabled.asStateFlow()

    private val _isVibrationEnabled = MutableStateFlow(settingsManager.isVibrationEnabled)
    val isVibrationEnabled: StateFlow<Boolean> = _isVibrationEnabled.asStateFlow()

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private var currentLevelFileName: String = ""

    fun loadLevel(fileName: String) {
        // استخراج اسم المستوى من اسم الملف (مثلاً: "easy_questions.json" يصبح "easy")
        val levelName = fileName.replace("_questions.json", "")
        
        val allQuestions = repository.loadQuestionsByLevel(fileName)
        
        if (allQuestions.isNotEmpty()) {
            currentLevelFileName = fileName
            
            // 1. قراءة ذاكرة الأسئلة الملعوبة مسبقاً
            var playedIds = settingsManager.getPlayedQuestions(levelName)
            
            // 2. تصفية الأسئلة (نستبعد ما تم لعبه)
            var availableQuestions = allQuestions.filter { !playedIds.contains(it.id.toString()) }
            
            // 3. إذا كانت الأسئلة المتبقية أقل من 10 (اللاعب أنهى المستوى)، نمسح الذاكرة ونبدأ من جديد
            if (availableQuestions.size < 10) {
                settingsManager.resetPlayedQuestions(levelName)
                playedIds = emptySet()
                availableQuestions = allQuestions
            }
            
            // 4. خلط الأسئلة المتبقية واختيار 10 أسئلة بشكل عشوائي
            val selectedQuestions = availableQuestions.shuffled().take(10)
            
            // 5. حفظ هذه الـ 10 أسئلة في الذاكرة لكي لا تظهر في الجولات القادمة
            val selectedIds = selectedQuestions.map { it.id.toString() }.toSet()
            settingsManager.savePlayedQuestions(levelName, selectedIds)
            
            // 6. الخلط العميق: خلط ترتيب الخيارات (الأجوبة) داخل كل سؤال وتحديث مؤشر الإجابة الصحيحة
            val finalQuestions = selectedQuestions.map { question ->
                // نحفظ النص الخاص بالإجابة الصحيحة
                val correctAnswerText = question.options[question.correct_index]
                // نخلط الخيارات عشوائياً
                val shuffledOptions = question.options.shuffled()
                // نبحث عن مكان الإجابة الصحيحة الجديد بعد الخلط
                val newCorrectIndex = shuffledOptions.indexOf(correctAnswerText)
                
                // نحدث السؤال بالخيارات الجديدة والمكان الجديد للإجابة
                question.copy(options = shuffledOptions, correct_index = newCorrectIndex)
            }
            
            _questions.value = finalQuestions
            _currentQuestionIndex.value = 0
            _score.value = 0
            _isGameActive.value = true
            _isGameOver.value = false
            _isSettingsActive.value = false
        }
    }

    // الدالة الأولى: تعمل فوراً عند الضغط لرفع النقاط وتشغيل الصوت
    fun processAnswer(isCorrect: Boolean) {
        if (isCorrect) {
            _score.value += 10
            soundManager.playCorrectSound()
        } else {
            soundManager.playWrongSound()
        }
    }

    // الدالة الثانية: تعمل بعد ثانيتين للانتقال للسؤال التالي
    fun moveToNextQuestion() {
        if (_currentQuestionIndex.value < _questions.value.size - 1) {
            _currentQuestionIndex.value += 1
        } else {
            _isGameActive.value = false
            _isGameOver.value = true
            soundManager.playWinSound()
        }
    }

    fun useHint() {
        val newScore = _score.value - 5
        _score.value = if (newScore > 0) newScore else 0
    }

    fun returnHome() {
        _isGameOver.value = false
        _isGameActive.value = false
        _isSettingsActive.value = false
        _questions.value = emptyList()
    }

    fun replayLevel() {
        if (currentLevelFileName.isNotEmpty()) loadLevel(currentLevelFileName)
    }

    fun openSettings() { _isSettingsActive.value = true }
    fun closeSettings() { _isSettingsActive.value = false }

    fun toggleSound(enabled: Boolean) {
        settingsManager.isSoundEnabled = enabled
        _isSoundEnabled.value = enabled
    }

    fun toggleVibration(enabled: Boolean) {
        settingsManager.isVibrationEnabled = enabled
        _isVibrationEnabled.value = enabled
    }
}
