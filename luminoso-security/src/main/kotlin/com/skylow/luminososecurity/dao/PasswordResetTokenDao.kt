package com.skylow.luminososecurity.dao

import com.skylow.luminososecurity.models.security.PasswordResetToken
import com.skylow.luminososecurity.models.security.user.AuthUserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*
import java.util.stream.Stream

interface PasswordResetTokenDao : JpaRepository<PasswordResetToken, Long> {

    fun findByToken(token: String): PasswordResetToken

    fun findByAuthUser(authUserEntity: AuthUserEntity): PasswordResetToken?

    fun findAllByExpiryDateLessThan(now: Date): Stream<PasswordResetToken>

    fun deleteByExpiryDateLessThan(now: Date)

    @Modifying
    @Query("delete from \"password_reset_token\" t where t.expiryDate <= ?;", nativeQuery = true)
    fun deleteAllExpiredSince(now: Date)

}