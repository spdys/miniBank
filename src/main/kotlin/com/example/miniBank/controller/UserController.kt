package com.example.miniBank.controller

import com.example.miniBank.MiniBankException
import com.example.miniBank.dto.request.CreateOrUpdateKycRequest
import com.example.miniBank.dto.request.UserCredentialsRequest
import com.example.miniBank.dto.response.FailureResponse
import com.example.miniBank.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/users/v1")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody request: UserCredentialsRequest) {
        userService.registerUser(request)
    }

    @PostMapping("/kyc")
    fun createOrUpdateKyc(@RequestBody request: CreateOrUpdateKycRequest): ResponseEntity<*> {
        return try {
            ResponseEntity.ok(userService.createOrUpdateKyc(request))
        } catch (e: MiniBankException) {
            ResponseEntity.badRequest().body(FailureResponse(e.message ?: "Something went wrong."))
        }
    }

    @GetMapping("/kyc/{userId}")
    fun getKyc(@PathVariable userId: Long): ResponseEntity<*> {
        return try {
            ResponseEntity.ok(userService.getKyc(userId))
        } catch (e: MiniBankException) {
            ResponseEntity.badRequest().body(FailureResponse(e.message ?: "Something went wrong."))
        }
    }
}