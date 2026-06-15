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
        val loadedQuestions = repository.loadQuestionsByLevel(fileName)
        if (loadedQuestions.isNotEmpty()) {
            currentLevelFileName = fileName
            _questions.value = loadedQuestions
            _currentQuestionIndex.value = 0
            _score.value = 0
            _isGameActive.value = true
            _isGameOver.value = false
            _isSettingsActive.value = false
        }
    }

    fun answerQuestion(isCorrect: Boolean) {
        if (isCorrect) {
            _score.value += 10
            soundManager.playCorrectSound()
        } else {
            soundManager.playWrongSound()
        }
        
        if (_currentQuestionIndex.value < _questions.value.size - 1) {
            _currentQuestionIndex.value += 1
        } else {
            _isGameActive.value = false
            _isGameOver.value = true
            soundManager.playWinSound()
        }
    }

    // الدالة الجديدة لخصم النقاط عند استخدام التلميح
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
