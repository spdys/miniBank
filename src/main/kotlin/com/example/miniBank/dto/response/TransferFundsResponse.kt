package com.example.miniBank.dto.response

import java.math.BigDecimal

data class TransferFundsResponse(
    val newBalance: BigDecimal
)