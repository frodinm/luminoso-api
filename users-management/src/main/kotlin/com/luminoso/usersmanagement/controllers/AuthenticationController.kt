package com.luminoso.usersmanagement.controllers

import com.luminoso.usersmanagement.models.messaging.Tenant
import com.luminoso.usersmanagement.models.pojo.authentication.SignUp
import com.luminoso.usersmanagement.services.IAuthenticationService
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class AuthenticationController(private val authenticationService: IAuthenticationService) {

    @PostMapping("/signup")
    fun register(@RequestBody signUp: SignUp, request: ServerHttpRequest): Mono<ResponseEntity<Unit>> {
        val tenant = Tenant(signUp.companyName)
        return authenticationService.registerTenant(tenant,signUp).map {
            ResponseEntity(Unit, CREATED)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody signUp: SignUp, request: ServerHttpRequest): Mono<ResponseEntity<Unit>> {
        val tenant = Tenant(signUp.companyName)
        return authenticationService.registerTenant(tenant,signUp).map {
            ResponseEntity(Unit, CREATED)
        }
    }
}