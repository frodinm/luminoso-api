package com.luminoso.usersmanagement.controllers

import com.luminoso.usersmanagement.exceptions.UserAlreadyExistException
import com.luminoso.usersmanagement.models.exception.ApiError
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalErrorHandlerController {

    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(Exception::class)
    fun handle(ex: Exception,
               request: ServerHttpRequest, response: ServerHttpResponse): ResponseEntity<ApiError> {

        logger.error(ex.message, ex)

        // REFRESH TOKEN

        val error = if(ex.cause != null) {
            ex.cause
        } else {
            ex
        }

        return when (error) {
            is NullPointerException -> getErrorResponseEntity(ex, BAD_REQUEST)
            is BadCredentialsException -> getErrorResponseEntity(ex, BAD_REQUEST)
            is UserAlreadyExistException -> getErrorResponseEntity(ex, CONFLICT)
            else -> getErrorResponseEntity(ex, INTERNAL_SERVER_ERROR)
        }
    }

    private fun getErrorResponseEntity(ex: Exception, httpStatus: HttpStatus): ResponseEntity<ApiError> {
        return ResponseEntity(ApiError(ex.localizedMessage),httpStatus)
    }
}
