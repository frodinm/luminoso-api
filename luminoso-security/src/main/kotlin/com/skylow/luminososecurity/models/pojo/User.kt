package com.skylow.luminososecurity.models.pojo

import com.skylow.luminososecurity.validation.ValidEmail

data class User(
    val username: String,
    val firstName: String,
    val lastName: String,
    @ValidEmail
    val email: String,
    val password: String,
    val picture: String)

