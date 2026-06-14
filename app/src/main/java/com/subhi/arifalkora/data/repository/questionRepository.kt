package com.subhi.arifalkora.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.subhi.arifalkora.data.model.Question
import java.io.InputStreamReader

class QuestionRepository(private val context: Context) {

    fun loadQuestionsByLevel(fileName: String): List<Question> {
        return try {
            val inputStream = context.assets.open(fileName)
            val reader = InputStreamReader(inputStream)
            val listType = object : TypeToken<List<Question>>() {}.type
            val questions: List<Question> = Gson().fromJson(reader, listType)
            reader.close()

            // 1. اختيار 10 أسئلة عشوائية فقط من الملف!
            val selectedQuestions = questions.shuffled().take(10)

            // 2. خلط الخيارات داخل كل سؤال وتحديث الإجابة الصحيحة
            selectedQuestions.map { q ->
                // حفظ نص الإجابة الصحيحة قبل الخلط
                val correctAnswerText = q.options[q.correct_index]
                
                // خلط الخيارات الأربعة
                val shuffledOptions = q.options.shuffled()
                
                // البحث عن مكان الإجابة الصحيحة بعد الخلط
                val newCorrectIndex = shuffledOptions.indexOf(correctAnswerText)
                
                // إرجاع السؤال بالترتيب الجديد
                Question(
                    id = q.id,
                    level = q.level,
                    question = q.question,
                    options = shuffledOptions,
                    correct_index = newCorrectIndex,
                    hint = q.hint
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
