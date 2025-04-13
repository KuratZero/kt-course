package com.kiratnine.ktcourse.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Configuration
@SecurityScheme(
    name = "JWT",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT"
)
@SecurityRequirement(name = "JWT")
class OpenApiConfig {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("KtCourse API")
                    .description("Документация API для курса")
                    .version("1.0.1")
            )
            .addSecurityItem(io.swagger.v3.oas.models.security.SecurityRequirement().addList("JWT"))
            .components(
                io.swagger.v3.oas.models.Components().addSecuritySchemes(
                    "JWT",
                    io.swagger.v3.oas.models.security.SecurityScheme()
                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            )
    }
}