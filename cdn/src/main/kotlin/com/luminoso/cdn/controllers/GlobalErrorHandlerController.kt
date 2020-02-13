package com.luminoso.cdn.controllers

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import reactor.core.publisher.Mono

@ControllerAdvice
class GlobalErrorHandlerController {

    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(Exception::class)
    fun handle(ex: Exception,
               request: ServerHttpRequest, response: ServerHttpResponse): Mono<ResponseEntity<Any>> {

        logger.error(ex.message, ex)

        // REFRESH TOKEN

        val error = if(ex.cause != null) {
            ex.cause
        } else {
            ex
        }

        return when (error) {
            is NullPointerException -> getErrorResponseEntity(ex, BAD_REQUEST)
            else -> getErrorResponseEntity(ex, INTERNAL_SERVER_ERROR)
        }
    }

    private fun getErrorResponseEntity(ex: Exception, httpStatus: HttpStatus): Mono<ResponseEntity<Any>> {
        return Mono.just(ResponseEntity(httpStatus))
    }
}
