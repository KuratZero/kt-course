package com.kiratnine.ktcourse.exception

/**
 * @author Artemii Kazakov (kiratnine@)
 */
class BadRequestException(val fallbackMessage: String) : RuntimeException() {
    override val message: String?
        get() = fallbackMessage
}