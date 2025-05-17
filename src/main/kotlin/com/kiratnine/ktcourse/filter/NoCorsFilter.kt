package com.kiratnine.ktcourse.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Component
class NoCorsFilter : Filter {
    override fun doFilter(
        request: ServletRequest?,
        response: ServletResponse?,
        chain: FilterChain?
    ) {
        val req = request as HttpServletRequest
        val res = response as HttpServletResponse

        val origin = req.getHeader("Origin")

        if (origin != null) {
            res.setHeader("Access-Control-Allow-Origin", origin)
            res.setHeader("Access-Control-Allow-Credentials", "true")
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
            res.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization")
        }

        if (req.method == "OPTIONS") {
            res.status = HttpServletResponse.SC_OK
            return
        }

        chain!!.doFilter(request, response)
    }
}