package com.example.miniBank.authentication.jwt

import com.example.miniBank.dto.request.UserCredentialsRequest
import com.example.miniBank.dto.response.FailureResponse
import com.example.miniBank.dto.response.LoginResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.*
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users/v1")
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService
) {

    @PostMapping("/login")
    fun login(@RequestBody authRequest: UserCredentialsRequest): ResponseEntity<*> {
        return try {
            val authToken = UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
            val authentication = authenticationManager.authenticate(authToken)

            if (authentication.isAuthenticated) {
                val userDetails = userDetailsService.loadUserByUsername(authRequest.username)
                val token = jwtService.generateToken(userDetails.username)
                ResponseEntity.ok(LoginResponse(token))
            } else {
                throw UsernameNotFoundException("Invalid user request!")
            }
        } catch (e: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(FailureResponse("Invalid username or password."))
        }
    }
}