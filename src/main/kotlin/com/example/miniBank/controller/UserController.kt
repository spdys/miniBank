package com.example.miniBank.controller

import com.example.miniBank.dto.request.CreateOrUpdateKycRequest
import com.example.miniBank.dto.request.RegisterUserRequest
import com.example.miniBank.dto.response.KycResponse
import com.example.miniBank.service.UserService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/users/v1")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody request: RegisterUserRequest) {
        userService.registerUser(request)
    }

    @PostMapping("/kyc")
    fun createOrUpdateKyc(@RequestBody request: CreateOrUpdateKycRequest): KycResponse {
        return userService.createOrUpdateKyc(request)
    }

    @GetMapping("/kyc/{userId}")
    fun getKyc(@PathVariable userId: Long): KycResponse {
        return userService.getKyc(userId)
    }
}