package com.luminoso.authorization.usecases.user.impl

import com.luminoso.authorization.repository.PasswordResetTokenRepository
import com.luminoso.authorization.repository.UserRepository
import com.luminoso.authorization.repository.VerificationTokenRepository
import com.luminoso.authorization.models.entities.security.user.UserEntity
import com.luminoso.authorization.usecases.user.IDeleteUserUseCase
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Component
class DeleteUserUseCase(private val userDao: UserRepository,
                        private val verificationTokenDao: VerificationTokenRepository,
                        private val passwordResetTokenDao: PasswordResetTokenRepository) : IDeleteUserUseCase {
    override fun delete(userEntity: UserEntity) {
        Mono.fromCallable {
            val verificationToken = verificationTokenDao.findByAuthUser(userEntity)
            if (verificationToken != null) {
                verificationTokenDao.delete(verificationToken)
            }
            val passwordResetToken = passwordResetTokenDao.findByAuthUser(userEntity)
            if (passwordResetToken != null) {
                passwordResetTokenDao.delete(passwordResetToken)
            }
            userDao.delete(userEntity)
        }.subscribeOn(Schedulers.elastic()).subscribe()
    }
}