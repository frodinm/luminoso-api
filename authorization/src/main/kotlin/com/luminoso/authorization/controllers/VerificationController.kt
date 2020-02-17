package com.luminoso.authorization.controllers

import com.luminoso.authorization.enums.TokenStatus
import com.luminoso.authorization.models.auth.ResendToken
import com.luminoso.authorization.usecases.verification.ICreateVerificationTokenUseCase
import com.luminoso.authorization.usecases.verification.IValidateVerificationTokenUseCase
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/verify")
class VerificationController(
    private val verificationTokenUseCase: IValidateVerificationTokenUseCase,
    private val createVerificationTokenUseCase: ICreateVerificationTokenUseCase
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/{token}")
    fun validateToken(@PathVariable("token") token: String, request: HttpServletRequest): Mono<ResponseEntity<Nothing>> {
        return verificationTokenUseCase.validate(token).map {
            if (it != TokenStatus.TOKEN_VALID.name) {
                ResponseEntity.badRequest().body(null)
            } else {
                ResponseEntity.ok().body(null)
            }
        }
    }

    @PostMapping("/token/resend")
    fun resendToken(@RequestBody resendToken: ResendToken, request: HttpServletRequest): Mono<ResponseEntity<Nothing>> {
        return createVerificationTokenUseCase.generateNewToken(resendToken.existingToken).map {
//            sendNewUserRegisteredMailUseCase.resend(it.token)
            ResponseEntity.ok().body(null)
        }
    }

}
