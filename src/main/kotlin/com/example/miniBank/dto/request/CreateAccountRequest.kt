package com.example.miniBank.dto.request

import java.math.BigDecimal

data class CreateAccountRequest (
    val userId: Long,
    val name: String,
    val balance: BigDecimal
)