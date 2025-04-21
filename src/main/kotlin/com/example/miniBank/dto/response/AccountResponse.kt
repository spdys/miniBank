package com.example.miniBank.dto.response

import java.math.BigDecimal

data class AccountResponse(
    val userId: Long,
    val accountNumber: String,
    val name: String,
    val balance: BigDecimal
)