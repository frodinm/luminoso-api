package com.luminoso.authorization.models.auth.body


data class RegisterUserBody(
    val username: String,
    val email: String,
    val password: String
)

