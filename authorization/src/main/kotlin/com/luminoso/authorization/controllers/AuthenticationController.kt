package com.luminoso.authorization.controllers

import com.luminoso.authorization.models.pojo.User
import com.luminoso.authorization.usecases.user.ICreateUserUseCase
import com.luminoso.authorization.usecases.cookie.IAuthenticationCookieUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/auth")
class AuthenticationController(private val createUserUseCase: ICreateUserUseCase,
                               private val authenticationCookieUseCase: IAuthenticationCookieUseCase) {

    @PostMapping("/signup")
    fun signup(@RequestBody user: User, request: HttpServletRequest): Mono<ResponseEntity<Nothing>> {
        return createUserUseCase.email(user).map {
            ResponseEntity.status(HttpStatus.CREATED).body(null)
        }
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): Mono<ResponseEntity<Nothing>> {
        return Mono.fromCallable {
            authenticationCookieUseCase.remove(request, response)
            ResponseEntity.ok().body(null)
        }
    }

}