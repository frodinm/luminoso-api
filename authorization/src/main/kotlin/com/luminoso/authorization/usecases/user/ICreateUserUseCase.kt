package com.luminoso.authorization.usecases.user

import com.luminoso.authorization.models.pojo.User
import com.luminoso.authorization.models.entities.security.user.UserEntity
import com.luminoso.authorization.models.auth.body.RegisterUserBody
import reactor.core.publisher.Mono

interface ICreateUserUseCase {
    fun email(user: RegisterUserBody): Mono<UserEntity>
    fun sso(user: User): Mono<UserEntity>
}
