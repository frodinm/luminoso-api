package com.luminoso.authorization.controllers

import com.luminoso.authorization.models.exceptions.EmailNotVerifiedException
import com.luminoso.authorization.models.exceptions.UserAlreadyExistException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class ControllerGlobalErrorHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(Exception::class)
    fun handle(ex: Exception,
               request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<String> {

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
            is EmailNotVerifiedException -> getErrorResponseEntity(ex, UNAUTHORIZED)
            else -> getErrorResponseEntity(ex, INTERNAL_SERVER_ERROR)
        }
    }

    private fun getErrorResponseEntity(ex: Exception, httpStatus: HttpStatus): ResponseEntity<String> {
        return ResponseEntity(ex.localizedMessage,httpStatus)
    }
}