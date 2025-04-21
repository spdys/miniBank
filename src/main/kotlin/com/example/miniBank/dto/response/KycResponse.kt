package com.example.miniBank.dto.response

import java.math.BigDecimal
import java.time.LocalDate

data class KycResponse(
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val salary: BigDecimal
)