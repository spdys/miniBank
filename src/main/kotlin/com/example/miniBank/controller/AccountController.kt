package com.example.miniBank.controller

import com.example.miniBank.dto.request.CreateAccountRequest
import com.example.miniBank.dto.request.TransferFundsRequest
import com.example.miniBank.dto.response.AccountResponse
import com.example.miniBank.dto.response.ListAccountsResponse
import com.example.miniBank.dto.response.TransferFundsResponse
import com.example.miniBank.service.AccountService
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
    fun closeAccount(@PathVariable accountNumber: String) {
        accountService.closeAccount(accountNumber)
    }

    @PostMapping("transfer")
    fun transferFunds(@RequestBody request: TransferFundsRequest): TransferFundsResponse {
        return accountService.transferFunds(request)
    }
}