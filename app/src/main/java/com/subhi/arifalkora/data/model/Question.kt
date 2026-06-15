package com.subhi.arifalkora.data.model

import androidx.annotation.Keep

/**
 * هذا هو القالب الذي سيقوم بتحويل نصوص الـ JSON إلى كائنات برمجية (Objects)
 * كلمة @Keep تمنع تشفير هذا القالب في النسخة النهائية لكي لا تتعطل الأسئلة
 */
@Keep
data class Question(
    val id: Int,
    val level: String,
    val question: String,
    val options: List<String>,
    val correct_index: Int,
    val hint: String
)
