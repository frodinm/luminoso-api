package com.luminoso.authorization.repository

import com.luminoso.authorization.models.entities.security.user.UserEntity
import com.luminoso.authorization.models.entities.security.VerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import java.util.stream.Stream

@Repository
interface VerificationTokenRepository : JpaRepository<VerificationToken, Long> {

    fun findByToken(token: String): VerificationToken?

    fun findByAuthUser(userEntity: UserEntity): VerificationToken?

    fun findAllByExpiryDateLessThan(now: Date): Stream<VerificationToken>

    fun deleteByExpiryDateLessThan(now: Date)

    @Modifying
    @Query("delete from VerificationToken t where t.expiryDate <= ?1")
    fun deleteAllExpiredSince(now: Date)
}