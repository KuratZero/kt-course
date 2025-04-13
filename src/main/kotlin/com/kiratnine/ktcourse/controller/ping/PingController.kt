package com.kiratnine.ktcourse.controller.ping

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@RestController
@RequestMapping("/ping")
class PingController {
    @GetMapping
    fun ping() = "Ping is ok"
}