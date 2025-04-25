package com.example.miniBank.controller

import com.example.miniBank.*
import com.example.miniBank.dto.request.CreateAccountRequest
import com.example.miniBank.dto.request.TransferFundsRequest
import com.example.miniBank.dto.response.AccountResponse
import com.example.miniBank.dto.response.FailureResponse
import com.example.miniBank.dto.response.ListAccountsResponse
import com.example.miniBank.dto.response.TransferFundsResponse
import com.example.miniBank.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts/v1/accounts")
class AccountController(private val accountService: AccountService) {

    @PostMapping()
    fun createAccount(@RequestBody request: CreateAccountRequest): AccountResponse {
        return accountService.createAccount(request)
    }

    @GetMapping()
    fun listAccounts(): ListAccountsResponse {
        return accountService.listAccounts()
    }

    @PostMapping("{accountNumber}/close")
    fun closeAccount(@PathVariable accountNumber: String): ResponseEntity<*> {
        return try {
            accountService.closeAccount(accountNumber)
            ResponseEntity.ok().body(null)
        } catch (e: MiniBankException) {
            ResponseEntity.badRequest().body(
                FailureResponse(e.message ?: "Couldn't close account.")
            )
        }
    }

    @PostMapping("transfer")
    fun transferFunds(@RequestBody request: TransferFundsRequest): ResponseEntity<*> {
        return try {
            val result = accountService.transferFunds(request)
            ResponseEntity.ok(TransferFundsResponse(result))
        } catch (e: MiniBankException) {
            ResponseEntity.badRequest().body(FailureResponse(e.message ?: "Transfer failed."))
        }
    }
}