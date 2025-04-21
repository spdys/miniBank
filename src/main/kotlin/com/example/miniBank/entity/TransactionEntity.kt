package com.example.miniBank.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "transactions")
data class TransactionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var sourceAccountNumber: String,
    var destinationAccountNumber: String,

    var amount: BigDecimal,
) {
    constructor(): this(
        0,
        "",
        "",
        BigDecimal.ZERO,
    )
}