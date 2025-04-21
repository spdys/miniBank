package com.example.miniBank.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "accounts")
data class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne // user can create many accounts
    @JoinColumn(name = "user_id")
    var user: UserEntity,

    var accountNumber: String,
    var name: String,
    var balance: BigDecimal,
    var isActive: Boolean,
) {
    constructor(): this(
        0,
        UserEntity(),
        "",
        "",
        BigDecimal.ZERO,
        true
    )
}