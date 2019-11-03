package com.skylow.luminosorealestate.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/spaces")
class SpacesController {

    @GetMapping("/post")
    fun postSpace(): Mono<String> {
        return Mono.just("postSpace")
    }
}