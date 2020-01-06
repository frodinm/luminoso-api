package com.luminoso.authorization.usecases.verification

import com.luminoso.authorization.models.entities.security.VerificationToken
import reactor.core.publisher.Mono

interface ICreateVerificationTokenUseCase {
    fun generateNewToken(existingToken: String): Mono<VerificationToken>
}