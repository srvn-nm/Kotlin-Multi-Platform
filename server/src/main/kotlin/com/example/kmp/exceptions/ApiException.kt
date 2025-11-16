package com.example.kmp.exceptions

sealed class ApiException(message: String) : RuntimeException(message)

class NotFoundException(message: String = "Not found") : ApiException(message)
class UnauthorizedException(message: String = "Unauthorized") : ApiException(message)
class BadRequestException(message: String = "Bad request") : ApiException(message)
class DatabaseException(message: String = "Database error") : ApiException(message)
