package com.kiratnine.ktcourse.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Component
class JwtUtil {
    private val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512)

    private val expirationMs = 365 * 24 * 60 * 60 * 1000 // One year

    fun generateToken(login: String, role: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expirationMs)

        return Jwts.builder()
            .setSubject(login)
            .claim("role", role)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(secretKey)
            .compact()
    }

    fun getUsername(token: String): String =
        parseClaims(token).body.subject

    fun validateToken(token: String): Boolean = try {
        parseClaims(token)
        true
    } catch (_: JwtException) {
        false
    }

    private fun parseClaims(token: String): Jws<Claims> =
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
}