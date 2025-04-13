package com.kiratnine.ktcourse.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@ControllerAdvice
class RestExceptionHandler {
    @ExceptionHandler(BadRequestException::class)
    fun badRequestException(e: BadRequestException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<ErrorResponse> =
        ResponseEntity(
            ErrorResponse(
                timestamp = LocalDateTime.now(ZoneId.systemDefault()),
                error = HttpStatus.NOT_FOUND.name,
                message = e.message ?: "No Message",
            ),
            HttpStatus.NOT_FOUND
        )

    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                timestamp = LocalDateTime.now(),
                error = "Unexpected error",
                message = ex.message ?: "Something went wrong"
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}