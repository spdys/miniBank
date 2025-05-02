package com.example.miniBank

import com.example.miniBank.dto.response.FailureResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MiniBankException::class)
    fun handleMiniBankException(ex: MiniBankException): ResponseEntity<FailureResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(FailureResponse(ex.message ?: "An error occurred."))
    }
}