package com.example.miniBank.dto.request

import java.math.BigDecimal

data class TransferFundsRequest(
    val sourceAccountNumber: String,
    val destinationAccountNumber: String,
    val amount: BigDecimal
)