package com.luminoso.authorization.usecases.user

import com.luminoso.authorization.models.pojo.User
import com.luminoso.authorization.models.entities.security.user.UserEntity
import reactor.core.publisher.Mono

interface ICreateUserUseCase {
    fun email(user: User): Mono<UserEntity>
    fun sso(user: User): Mono<UserEntity>
}