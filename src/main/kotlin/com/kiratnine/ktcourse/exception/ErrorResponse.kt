package com.kiratnine.ktcourse.exception

import java.time.LocalDateTime

/**
 * @author Artemii Kazakov (kiratnine@)
 */
data class ErrorResponse(
    val timestamp: LocalDateTime,
    val error: String,
    val message: String,
)