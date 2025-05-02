package com.example.miniBank.service

import com.example.miniBank.KycInfoNotFound
import com.example.miniBank.UserIdNotFound
import com.example.miniBank.UsernameAlreadyExistsException
import com.example.miniBank.InvalidPasswordException
import com.example.miniBank.dto.request.CreateOrUpdateKycRequest
import com.example.miniBank.dto.request.UserCredentialsRequest
import com.example.miniBank.dto.response.KycResponse
import com.example.miniBank.entity.KycEntity
import com.example.miniBank.entity.UserEntity
import com.example.miniBank.repository.KycRepository
import com.example.miniBank.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val kycRepository: KycRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun registerUser(request: UserCredentialsRequest) {
        if (userRepository.findByUsername(request.username) != null)
            throw UsernameAlreadyExistsException()
        if (request.password.length < 6)
            throw InvalidPasswordException("Password must be at least 6 characters long.")
        if (!request.password.any { it.isUpperCase() })
            throw InvalidPasswordException("Password must contain at least one uppercase letter.")
        if (!request.password.any { it.isDigit() })
            throw InvalidPasswordException("Password must contain at least one number.")

        val user = UserEntity(
            0,
            request.username,
            passwordEncoder.encode(request.password)
        )

        userRepository.save(user)
    }

    fun createOrUpdateKyc(request: CreateOrUpdateKycRequest): KycResponse {
        val user = userRepository.findById(request.userId)
            .orElseThrow { UserIdNotFound(request.userId) }

        val existingKyc = kycRepository.findByUserId(request.userId)

        val kyc =
            // if kyc exists, update
            existingKyc?.apply {
                firstName = request.firstName
                lastName = request.lastName
                dateOfBirth = request.dateOfBirth
                salary = request.salary

            } // else if null, create new
                ?: KycEntity(
                    0,
                    user,
                    request.firstName,
                    request.lastName,
                    request.dateOfBirth,
                    request.salary
                )

        val savedKyc = kycRepository.save(kyc)

        return KycResponse(
            savedKyc.user.id,
            savedKyc.firstName,
            savedKyc.lastName,
            savedKyc.dateOfBirth,
            savedKyc.salary
        )
    }

    fun getKyc(userId: Long): KycResponse {
        val kyc = kycRepository.findByUserId(userId)
            ?: throw KycInfoNotFound(userId)

        return KycResponse(
            kyc.user.id,
            kyc.firstName,
            kyc.lastName,
            kyc.dateOfBirth,
            kyc.salary
        )
    }
}