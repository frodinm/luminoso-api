package com.luminoso.authorization.usecases.verification

import reactor.core.publisher.Mono

interface IValidateVerificationTokenUseCase {
    fun validate(token: String): Mono<String>
}