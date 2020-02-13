package com.luminoso.authorization.models.messaging

class UserCreatedEvent(val email: String, val verificationToken: String)
