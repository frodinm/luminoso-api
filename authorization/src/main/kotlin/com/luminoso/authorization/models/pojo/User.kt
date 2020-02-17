package com.luminoso.authorization.models.pojo

import java.util.*


data class User(
    val uuid: UUID = UUID.randomUUID(),
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val picture: String
)

