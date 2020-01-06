package com.luminoso.authorization.usecases.user

import com.luminoso.authorization.models.entities.security.user.UserEntity

interface IDeleteUserUseCase {
    fun delete(userEntity: UserEntity)
}