package com.example.miniBank.service

import com.example.miniBank.dto.request.CreateAccountRequest
import com.example.miniBank.dto.request.TransferFundsRequest
import com.example.miniBank.dto.response.*
import com.example.miniBank.entity.AccountEntity
import com.example.miniBank.entity.TransactionEntity
import com.example.miniBank.repository.*
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
) {
    fun createAccount(request: CreateAccountRequest): AccountResponse {
        val user = userRepository.findById(request.userId)
            .orElseThrow { RuntimeException("User not found.") }

        val generatedAccountNumber = "ACC" + (100000..999999).random()

        val account = AccountEntity(
            0,
            user,
            generatedAccountNumber,
            request.name,
            request.balance,
            true
        )

        val savedAccount = accountRepository.save(account)

        return AccountResponse(
            savedAccount.user.id,
            savedAccount.accountNumber,
            savedAccount.name,
            savedAccount.balance,
        )
    }

    fun listAccounts(): ListAccountsResponse {
        val accounts = accountRepository.findAll()
            .sortedByDescending { it.isActive } // want active first; more important

        val accountResponses = accounts.map { account ->
            AccountResponse(
                account.user.id,
                account.accountNumber,
                account.name,
                account.balance
            )
        }

        return ListAccountsResponse(accountResponses)
    }

    fun closeAccount(accountNumber: String) {
        val account = accountRepository.findByAccountNumber(accountNumber)
            ?: throw RuntimeException("Account not found.")

        account.isActive = false
        accountRepository.save(account)
    }

    fun transferFunds(request: TransferFundsRequest): TransferFundsResponse {
        // checking for errors before we begin
        val source = accountRepository.findByAccountNumber(request.sourceAccountNumber)
            ?: throw RuntimeException("Source account not found.")

        val destination = accountRepository.findByAccountNumber(request.destinationAccountNumber)
            ?: throw RuntimeException("Destination account not found.")

        if (source.accountNumber == destination.accountNumber) throw RuntimeException("Cannot transfer to same account.")

        if (!destination.isActive) throw RuntimeException("Cannot transfer to closed account.")

        if (source.balance < request.amount) throw RuntimeException("Insufficient funds.")

        // if everything passed, actually transfer the funds
        source.balance = source.balance.minus(request.amount)
        destination.balance = destination.balance.plus(request.amount)

        accountRepository.save(source)
        accountRepository.save(destination)

        // not asked for, but to record transaction:
        val transaction = TransactionEntity(
            0,
            source.accountNumber,
            destination.accountNumber,
            request.amount
        )

        return TransferFundsResponse(source.balance)
    }
}