package com.subhi.arifalkora.data.model

/**
 * هذا هو القالب الذي سيقوم بتحويل نصوص الـ JSON إلى كائنات برمجية (Objects)
 * لاحظ أن أسماء المتغيرات هنا تطابق تماماً الأسماء الموجودة في ملفات JSON الخاصة بك
 */
data class Question(
    val id: Int,
    val level: String,
    val question: String,
    val options: List<String>,
    val correct_index: Int,
    val hint: String
)
