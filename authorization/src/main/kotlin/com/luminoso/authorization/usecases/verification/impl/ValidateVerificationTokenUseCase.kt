package com.luminoso.authorization.usecases.verification.impl

import com.luminoso.authorization.enums.TokenStatus
import com.luminoso.authorization.repository.UserRepository
import com.luminoso.authorization.repository.VerificationTokenRepository
import com.luminoso.authorization.usecases.verification.IValidateVerificationTokenUseCase
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
class ValidateVerificationTokenUseCase(
    private val tokenRepository: VerificationTokenRepository,
    private val userRepository: UserRepository) : IValidateVerificationTokenUseCase {

    override fun validate(token: String): Mono<String> {
        return Mono.fromCallable {
            val verificationToken = tokenRepository.findByToken(token)

            if (verificationToken == null) {
                TokenStatus.TOKEN_INVALID.name
            } else {
                val user = verificationToken.authUser!!
                val cal = Calendar.getInstance()

                if (verificationToken.expiryDate.time.minus(cal.time.time) <= 0) {
                    tokenRepository.delete(verificationToken)
                    TokenStatus.TOKEN_EXPIRED.name
                } else {
                    user.emailVerified = true
                    userRepository.save(user)

                    TokenStatus.TOKEN_VALID.name
                }
            }
        }.subscribeOn(Schedulers.elastic())
    }
}