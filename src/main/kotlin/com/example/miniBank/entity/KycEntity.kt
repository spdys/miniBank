package com.example.miniBank.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "kyc")
data class KycEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @OneToOne // user = customer, know your customer
    @JoinColumn(name = "user_id")
    var user: UserEntity,

    var firstName: String,
    var lastName: String,
    var dateOfBirth: LocalDate,
    var salary: BigDecimal

) {
    constructor(): this(
        0,
        UserEntity(),
        "",
        "",
        LocalDate.now(),
        BigDecimal.ZERO
    )
}
