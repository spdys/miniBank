package com.example.miniBank.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var username: String,
    var password: String,

) {
    constructor(): this(
        0,
        "",
        "",
    )
}