package com.example.miniBank.repository

import com.example.miniBank.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<AccountEntity, Long> {
    fun findByAccountNumber(accountNumber: String): AccountEntity?
}