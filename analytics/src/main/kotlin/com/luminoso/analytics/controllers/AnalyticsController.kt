package com.luminoso.analytics.controllers

import com.luminoso.analytics.models.controllers.body.EventBody
import com.luminoso.analytics.models.controllers.response.EventResponse
import com.luminoso.analytics.service.analytics.IAnalyticsService
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.security.Principal
import javax.servlet.http.HttpServletRequest

@RestController
class AnalyticsController(private val analyticsService: IAnalyticsService) {
    @PostMapping("/event")
    fun postEvent(@RequestBody eventBody: EventBody, request: HttpServletRequest, principal: Principal): Mono<ResponseEntity<EventResponse>> {
        val userUniqueId = (principal as JwtAuthenticationToken).token.claims["sub"] as String
        return analyticsService.postEvent(eventBody,request, userUniqueId).map {
            ResponseEntity.ok().body(it)
        }
    }
}
