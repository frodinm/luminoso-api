package com.luminoso.authorization.controllers

import com.luminoso.authorization.models.api.body.InstrospectApiKeyBody
import com.luminoso.authorization.service.apikey.IApiKeyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.security.Principal

@RestController
@RequestMapping("/oauth/apikey")
class ApiKeyController(private val apiKeyService: IApiKeyService) {

    @PostMapping("/introspect")
    fun introspectApiKey(@RequestBody body: InstrospectApiKeyBody): Mono<ResponseEntity<HttpStatus>> {
        return Mono.just(ResponseEntity.status(HttpStatus.OK).build())
    }

}
