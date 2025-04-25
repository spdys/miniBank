package com.example.miniBank

open class TransferException(message: String) : RuntimeException(message)

class AccountNotFound: TransferException("Account not found.")
class SourceNotFound: TransferException("Source account not found.")
class DestinationNotFound: TransferException("Destination account not found.")
class SameAccountException: TransferException("Cannot transfer to same account.")
class AccountClosed: TransferException("Cannot transfer to closed account.")
class InsufficientFunds: TransferException("Insufficient funds.")
class NotNonNegative: TransferException("Transfer amount must be greater than zero.")

// user
class UserIdNotFound: Exception()
class KycInfoNotFound: Exception()