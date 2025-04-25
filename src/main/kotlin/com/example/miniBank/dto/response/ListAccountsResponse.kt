package com.example.miniBank.dto.response

data class ListAccountsResponse(
    val activeAccounts: List<AccountResponse>,
    val inactiveAccounts: List<AccountResponse>
)