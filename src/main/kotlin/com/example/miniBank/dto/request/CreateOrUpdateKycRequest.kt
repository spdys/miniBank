package com.example.miniBank.dto.request

import java.math.BigDecimal
import java.time.LocalDate

data class CreateOrUpdateKycRequest(
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val salary: BigDecimal
)