package com.luminoso.authorization.usecases.user

import com.luminoso.authorization.models.entities.security.user.UserEntity
import reactor.core.publisher.Mono

interface IGetUserUseCase {
    fun byId(userId: Int): Mono<UserEntity?>
    fun byEmail(email: String): Mono<UserEntity?>
}