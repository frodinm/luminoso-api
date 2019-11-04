package com.skylow.luminosorealestate.controllers

import ApiResponse
import com.skylow.luminosorealestate.models.EmptyJsonResponse
import com.skylow.luminosorealestate.models.spaces.PostSpaceBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/spaces")
class SpacesController {

    @PostMapping("/post")
    fun postSpace(@RequestBody postSpaceBody: PostSpaceBody): Mono<ResponseEntity<ApiResponse<EmptyJsonResponse>>> {
        return Mono.just(ResponseEntity(ApiResponse(true, EmptyJsonResponse()),HttpStatus.OK))
    }
}