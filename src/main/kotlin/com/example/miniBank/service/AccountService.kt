package com.example.miniBank.service

import com.example.miniBank.*
import com.example.miniBank.dto.request.CreateAccountRequest
import com.example.miniBank.dto.request.TransferFundsRequest
import com.example.miniBank.dto.response.AccountResponse
import com.example.miniBank.dto.response.ListAccountsResponse
import com.example.miniBank.entity.AccountEntity
import com.example.miniBank.entity.TransactionEntity
import com.example.miniBank.repository.AccountRepository
import com.example.miniBank.repository.TransactionRepository
import com.example.miniBank.repository.UserRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
) {
    fun createAccount(request: CreateAccountRequest): AccountResponse {
        val user = userRepository.findById(request.userId)
            .orElseThrow { UserIdNotFound(request.userId) }

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

     fun mapToAccountResponse(accounts: List<AccountEntity>): List<AccountResponse> {
        return accounts.map { account ->
            AccountResponse(
                account.user.id,
                account.accountNumber,
                account.name,
                account.balance
            )
        }
    }

    fun listAccounts(): ListAccountsResponse {
        val accounts = accountRepository.findAll()

        val (activeAccounts, inactiveAccounts) = accounts.partition { it.isActive }

        return ListAccountsResponse(
            mapToAccountResponse(activeAccounts),
            mapToAccountResponse(inactiveAccounts)
        )
    }

        fun closeAccount(accountNumber: String) {
        val account = accountRepository.findByAccountNumber(accountNumber)
            ?: throw AccountNotFound(accountNumber)

        account.isActive = false
        accountRepository.save(account)
    }

    fun transferFunds(request: TransferFundsRequest): BigDecimal {
        // checking for errors before we begin
        val source = accountRepository.findByAccountNumber(request.sourceAccountNumber)
            ?: throw SourceNotFound(request.sourceAccountNumber)

        val destination = accountRepository.findByAccountNumber(request.destinationAccountNumber)
            ?: throw DestinationNotFound(request.destinationAccountNumber)

        if (source.accountNumber == destination.accountNumber) throw SameAccountException()
        if (!destination.isActive) throw AccountClosed()
        if (source.balance < request.amount) throw InsufficientFunds()
        if (request.amount <= BigDecimal.ZERO) throw NotNonNegative()

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

        transactionRepository.save(transaction)


        return source.balance
    }
}