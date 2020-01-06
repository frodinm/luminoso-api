package com.luminoso.authorization.usecases.user.impl

import com.luminoso.authorization.models.entities.security.user.UserEntity
import com.luminoso.authorization.repository.VerificationTokenRepository
import com.luminoso.authorization.usecases.user.IGetUserWithVerificationTokenUseCase
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.lang.Exception

@Component
class GetUserWithVerificationTokenUseCase(private val repository: VerificationTokenRepository) : IGetUserWithVerificationTokenUseCase {
    override fun get(verificationToken: String): Mono<UserEntity> {
        return Mono.fromCallable {
            val token = repository.findByToken(verificationToken) ?: throw Exception("Token was not found")
            token.authUser!!
        }.subscribeOn(Schedulers.elastic())
    }
}