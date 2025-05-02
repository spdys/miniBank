package com.example.miniBank

open class MiniBankException(message: String): RuntimeException(message)

class AccountNotFound(accountNumber: String): MiniBankException("Account $accountNumber not found.")
class SourceNotFound(accountNumber: String): MiniBankException("Source account $accountNumber not found.")
class DestinationNotFound(accountNumber: String): MiniBankException("Destination account $accountNumber not found.")

class SameAccountException: MiniBankException("Cannot transfer to same account.")
class AccountClosed: MiniBankException("Cannot transfer to closed account.")

class InsufficientFunds: MiniBankException("Insufficient funds.")
class NotNonNegative: MiniBankException("Transfer amount must be greater than zero.")

class UserIdNotFound(userId: Long): MiniBankException("User ID $userId not found.")
class KycInfoNotFound(userId: Long): MiniBankException("KYC info for user ID $userId not found.")
class UsernameAlreadyExistsException(): MiniBankException("Username already exists.")
class InvalidPasswordException(reason: String): MiniBankException("Invalid password: $reason")
