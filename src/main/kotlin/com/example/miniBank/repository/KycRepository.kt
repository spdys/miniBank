package com.example.miniBank.repository

import com.example.miniBank.entity.KycEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface KycRepository : JpaRepository<KycEntity, Long> {
    fun findByUserId(userId: Long): KycEntity?
}