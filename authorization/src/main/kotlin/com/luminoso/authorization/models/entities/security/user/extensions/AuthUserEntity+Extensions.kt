package com.luminoso.authorization.models.entities.security.user.extensions

import com.luminoso.authorization.models.entities.security.PasswordResetToken
import com.luminoso.authorization.models.entities.security.RoleEntity
import com.luminoso.authorization.models.entities.security.VerificationToken
import com.luminoso.authorization.models.entities.security.user.UserAuthProvider
import com.luminoso.authorization.models.entities.security.user.UserEntity
import com.luminoso.authorization.models.pojo.User

fun UserEntity.toUser(): User {
    return User(uuid, username, firstName, lastName, email, password, "")
}

fun UserEntity.toAuthUser(): AuthUser {
    return AuthUser(
        firstName,
        lastName,
        username,
        email,
        emailVerified,
        accountNonExpired,
        accountNonLocked,
        credentialsNonExpired,
        enabled,
        roles,
        userAuthProviders,
        verificationToken!!,
        passwordResetToken
    )
}

data class AuthUser(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val emailVerified: Boolean,
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val credentialsNonExpired: Boolean,
    val enabled: Boolean,
    val roles: Set<RoleEntity>,
    val userAuthProviders: List<UserAuthProvider>,
    val verificationToken: VerificationToken,
    val passwordResetToken: PasswordResetToken?
)
