package com.kiratnine.ktcourse.translator.client

import com.kiratnine.ktcourse.translator.client.model.TranslatorRequest
import com.kiratnine.ktcourse.translator.client.model.TranslatorResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.Optional

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Component
class TranslatorClient(
    private val translatorWebClient: WebClient,
    @Value("\${translator.folderId}") private val folderId: String,
) {
    fun getTranslate(text: String, targetLocale: String): Optional<TranslatorResponse> =
        translatorWebClient.post()
            .uri("/translate/v2/translate")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TranslatorRequest(folderId, listOf(text), targetLocale))
            .retrieve()
            .bodyToMono(TranslatorResponse::class.java)
            .blockOptional()
}