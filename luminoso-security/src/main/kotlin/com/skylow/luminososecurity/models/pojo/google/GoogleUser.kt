package com.skylow.luminososecurity.models.pojo.google

data class GoogleUser(
    val userId: String,
    val email: String,
    val emailVerified: Boolean,
    val name: String,
    val givenName: String,
    val familyName: String,
    val pictureUrl: String,
    val locale: String
)