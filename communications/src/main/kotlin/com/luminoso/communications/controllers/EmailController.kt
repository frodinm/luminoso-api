package com.luminoso.communications.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/email")
class EmailController {

    @PostMapping("/register")
    fun register(): Mono<ResponseEntity<Nothing>>{
        return Mono.just(ResponseEntity.ok().body(null))
    }
}
