package com.kiratnine.ktcourse.translator.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Configuration
class TranslatorClientConfiguration {
    @Bean
    fun translatorWebClient(@Value("\${translator.key}") key: String): WebClient =
        WebClient.builder()
            .baseUrl("https://translate.api.cloud.yandex.net")
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Api-Key $key")
            .build()
}