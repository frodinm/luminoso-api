package com.luminoso.authorization.models.pojo


data class User(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val picture: String)

