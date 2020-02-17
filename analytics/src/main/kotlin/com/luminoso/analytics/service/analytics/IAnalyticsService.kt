package com.luminoso.analytics.service.analytics

import com.luminoso.analytics.models.controllers.body.EventBody
import com.luminoso.analytics.models.controllers.response.EventResponse
import reactor.core.publisher.Mono
import java.security.Principal
import javax.servlet.http.HttpServletRequest

interface IAnalyticsService {
    fun postEvent(eventBody: EventBody,request: HttpServletRequest, principalId: String): Mono<EventResponse>
}
