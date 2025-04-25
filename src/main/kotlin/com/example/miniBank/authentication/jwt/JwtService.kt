package com.example.miniBank.authentication.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtService {

    private val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    private val expirationMs: Long = 1000 * 60 * 60  // milliseconds * seconds * minutes = 1 hour

    fun generateToken(username: String): String {
        val now = Date()
        val expiry = Date(now.time + expirationMs)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(secretKey)
            .compact()
    }

    fun extractUsername(token: String): String =
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject

    fun isTokenValid(token: String, username: String): Boolean {
        return try {
            extractUsername(token) == username
        } catch (e: Exception) {
            false
        }
    }
}
