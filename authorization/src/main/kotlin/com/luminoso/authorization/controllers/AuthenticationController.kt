package com.luminoso.authorization.controllers

import com.luminoso.authorization.models.auth.body.RegisterUserBody
import com.luminoso.authorization.models.auth.response.RegisterResponse
import com.luminoso.authorization.usecases.user.ICreateUserUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import javax.servlet.http.HttpServletRequest

@RestController
class AuthenticationController(private val createUserUseCase: ICreateUserUseCase) {

    @PostMapping("/register")
    fun register(@RequestBody user: RegisterUserBody, request: HttpServletRequest): Mono<ResponseEntity<RegisterResponse>> {
        return createUserUseCase.email(user).map {
            val userUUID = it.uuid.toString()
            val registerResponse = RegisterResponse(userUUID)
            ResponseEntity.status(HttpStatus.CREATED.value()).body(registerResponse)
        }
    }
}
