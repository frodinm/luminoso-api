package com.luminoso.authorization.repository

import com.luminoso.authorization.models.entities.security.PasswordResetToken
import com.luminoso.authorization.models.entities.security.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*
import java.util.stream.Stream

interface PasswordResetTokenRepository : JpaRepository<PasswordResetToken, Long> {

    fun findByToken(token: String): PasswordResetToken

    fun findByAuthUser(userEntity: UserEntity): PasswordResetToken?

    fun findAllByExpiryDateLessThan(now: Date): Stream<PasswordResetToken>

    fun deleteByExpiryDateLessThan(now: Date)

    @Modifying
    @Query("delete from \"password_reset_token\" t where t.expiryDate <= ?;", nativeQuery = true)
    fun deleteAllExpiredSince(now: Date)

}