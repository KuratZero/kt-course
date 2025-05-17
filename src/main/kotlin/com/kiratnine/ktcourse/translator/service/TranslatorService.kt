package com.kiratnine.ktcourse.translator.service

import com.kiratnine.ktcourse.model.LocalizedString
import com.kiratnine.ktcourse.translator.client.TranslatorClient
import org.springframework.stereotype.Service

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Service
class TranslatorService(
    private val translatorClient: TranslatorClient,
) {
    fun translateString(text: String): LocalizedString {
        return mutableMapOf(
            RU to text,
        ).also { map ->
            translatorClient.getTranslate(text, EN).ifPresent {
                map.put(EN, it.translations.first().text)
            }
        }
    }

    companion object {
        const val RU = "ru"
        const val EN = "en"
    }
}