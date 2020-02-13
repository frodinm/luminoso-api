package com.luminoso.authorization.models.messaging

data class UserCreatedPayload(val name: String, val email: String, val verificationToken: String, val os: String)
