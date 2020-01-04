package com.luminoso.usersmanagement.usecases.keycloak

import com.luminoso.usersmanagement.models.pojo.authentication.SignUp
import com.luminoso.usersmanagement.models.pojo.representations.idm.UserRepresentation

interface IUserUseCase {
    fun toUser(alias: String, signUp: SignUp): UserRepresentation
}