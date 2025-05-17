package com.kiratnine.ktcourse.translator.client.model

/**
 * @author Artemii Kazakov (kiratnine@)
 */
data class TranslatorResponseText(
    val text: String,
    val detectedLanguageCode: String,
)
