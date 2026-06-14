package com.subhi.arifalkora.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.subhi.arifalkora.data.model.Question
import java.io.InputStreamReader

/**
 * هذا الكلاس هو المسؤول الحصري عن جلب البيانات من ملفات الـ JSON
 */
class QuestionRepository(private val context: Context) {

    // دالة لاستدعاء الأسئلة بناءً على اسم الملف (مثلاً: "easy_questions.json")
    fun loadQuestionsByLevel(fileName: String): List<Question> {
        return try {
            // 1. فتح الملف من مجلد assets
            val inputStream = context.assets.open(fileName)
            val reader = InputStreamReader(inputStream)

            // 2. استخدام مكتبة Gson لتحويل النص إلى قائمة من الأسئلة المفهومة برمجياً
            val listType = object : TypeToken<List<Question>>() {}.type
            val questions: List<Question> = Gson().fromJson(reader, listType)
            
            // إغلاق القارئ لتوفير الذاكرة
            reader.close()
            
            // إرجاع الأسئلة بشكل عشوائي لكي لا يحفظ اللاعب الترتيب!
            questions.shuffled() 
            
        } catch (e: Exception) {
            // في حال حدوث أي خطأ (مثل عدم وجود الملف)، اطبع الخطأ وأرجع قائمة فارغة
            e.printStackTrace()
            emptyList()
        }
    }
}
