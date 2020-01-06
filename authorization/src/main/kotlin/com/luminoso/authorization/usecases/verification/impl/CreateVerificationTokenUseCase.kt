package com.luminoso.authorization.usecases.verification.impl

import com.luminoso.authorization.models.entities.security.VerificationToken
import com.luminoso.authorization.repository.VerificationTokenRepository
import com.luminoso.authorization.usecases.verification.ICreateVerificationTokenUseCase
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
class CreateVerificationTokenUseCase(private val repository: VerificationTokenRepository) : ICreateVerificationTokenUseCase {
    override fun generateNewToken(existingToken: String): Mono<VerificationToken> {
        return Mono.fromCallable {
            val verificationToken = repository.findByToken(existingToken)
                ?: throw Exception("VerificationToken was null")
            verificationToken.updateToken(UUID.randomUUID().toString())
            repository.save(verificationToken)
        }.subscribeOn(Schedulers.elastic())
    }
}