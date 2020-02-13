package com.luminoso.authorization.usecases.user.impl

import com.luminoso.authorization.models.entities.security.user.UserEntity
import com.luminoso.authorization.repository.UserRepository
import com.luminoso.authorization.repository.VerificationTokenRepository
import com.luminoso.authorization.usecases.user.IGetUserUseCase
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.lang.Exception

@Component
class GetUserUseCase(private val userRepository: UserRepository,
                     private val verificationTokenRepository: VerificationTokenRepository) : IGetUserUseCase {
    override fun byId(userId: Int): Mono<UserEntity?> {
        return Mono.fromCallable {
            userRepository.findById(userId).get()
        }.subscribeOn(Schedulers.elastic())
    }

    override fun byEmail(email: String): Mono<UserEntity?> {
        return Mono.fromCallable {
            userRepository.findByEmail(email)
        }.subscribeOn(Schedulers.elastic())
    }

    override fun byVerificationToken(verificationToken: String): Mono<UserEntity> {
        return Mono.fromCallable {
            val token = verificationTokenRepository.findByToken(verificationToken) ?: throw Exception("Token was not found")
            token.authUser!!
        }.subscribeOn(Schedulers.elastic())
    }
}
