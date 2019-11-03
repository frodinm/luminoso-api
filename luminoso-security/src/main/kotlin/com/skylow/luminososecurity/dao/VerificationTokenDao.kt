package com.skylow.luminososecurity.dao

import com.skylow.luminososecurity.models.security.VerificationToken
import com.skylow.luminososecurity.models.security.user.AuthUserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import java.util.stream.Stream

@Repository
interface VerificationTokenDao : JpaRepository<VerificationToken, Long> {

    fun findByToken(token: String): VerificationToken?

    fun findByAuthUser(authUserEntity: AuthUserEntity): VerificationToken?

    fun findAllByExpiryDateLessThan(now: Date): Stream<VerificationToken>

    fun deleteByExpiryDateLessThan(now: Date)

    @Modifying
    @Query("delete from VerificationToken t where t.expiryDate <= ?1")
    fun deleteAllExpiredSince(now: Date)
}