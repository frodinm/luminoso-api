package com.luminoso.authorization.usecases.user

import com.luminoso.authorization.models.entities.security.user.UserEntity
import reactor.core.publisher.Mono

interface IGetUserWithVerificationTokenUseCase {
    fun get(verificationToken: String): Mono<UserEntity>
}