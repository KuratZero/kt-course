package com.kiratnine.ktcourse.translator.client.model

/**
 * @author Artemii Kazakov (kiratnine@)
 */
data class TranslatorRequest(
    val folderId: String,
    val texts: List<String>,
    val targetLanguageCode: String,
)
