package com.subhi.arifalkora.data.repository

import android.content.Context
import com.google.gson.Gson
import com.subhi.arifalkora.data.model.Question
import java.io.InputStreamReader

class QuestionRepository(private val context: Context) {

    fun loadQuestionsByLevel(fileName: String): List<Question> {
        return try {
            val inputStream = context.assets.open(fileName)
            val reader = InputStreamReader(inputStream)
            
            // الحل السحري لتخطي مشكلة نظام الحماية: استخدام Array بدلاً من TypeToken
            val questionsArray = Gson().fromJson(reader, Array<Question>::class.java)
            val questions: List<Question> = questionsArray.toList()
            
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
