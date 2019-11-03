package com.skylow.luminososecurity.models.security.user.extensions

import com.skylow.luminososecurity.models.pojo.User
import com.skylow.luminososecurity.models.security.PasswordResetToken
import com.skylow.luminososecurity.models.security.Role
import com.skylow.luminososecurity.models.security.VerificationToken
import com.skylow.luminososecurity.models.security.user.AuthUserEntity

fun AuthUserEntity.toUser(): User {
    return User(username, firstName, lastName, email, password, "")
}

fun AuthUserEntity.toAuthUser(): AuthUser {
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
    val roles: Set<Role>,
    val verificationToken: VerificationToken,
    val passwordResetToken: PasswordResetToken?
)