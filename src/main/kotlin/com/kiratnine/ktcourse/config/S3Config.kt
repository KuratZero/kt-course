package com.kiratnine.ktcourse.config

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
import java.net.URI

object S3Config {
    private const val ENDPOINT_URL = "https://storage.yandexcloud.net"
    private val REGION = Region.of("ru-central1")

    val s3Client: S3Client = S3Client.builder()
        .region(REGION)
        .endpointOverride(URI.create(ENDPOINT_URL))
        .credentialsProvider(DefaultCredentialsProvider.create())
        .serviceConfiguration(
            S3Configuration.builder()
                .pathStyleAccessEnabled(true) // важно для Yandex Object Storage
                .build()
        )
        .httpClient(UrlConnectionHttpClient.create())
        .build()
}
