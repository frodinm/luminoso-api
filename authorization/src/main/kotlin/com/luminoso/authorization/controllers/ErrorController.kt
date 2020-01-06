package com.luminoso.authorization.controllers

import com.luminoso.authorization.models.exceptions.EmailNotVerifiedException
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import javax.servlet.http.HttpServletRequest


@Controller
class ErrorController : ErrorController {

    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest): ResponseEntity<*> {
        val exception = request.getAttribute("javax.servlet.error.exception") as Exception?

        return if (exception != null) {
            when (exception) {
                is EmailNotVerifiedException -> ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email not verified")
                else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.message)
            }
        } else {
            when (request.getAttribute("javax.servlet.error.status_code")) {
                401 -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
                else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
            }
        }
    }

    override fun getErrorPath(): String {
        return "/error"
    }
}