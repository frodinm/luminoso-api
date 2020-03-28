package com.luminoso.analytics.service.analytics.impl

import com.luminoso.analytics.models.controllers.body.EventBody
import com.luminoso.analytics.models.controllers.response.EventResponse
import com.luminoso.analytics.models.entities.analytics.AgentEntity
import com.luminoso.analytics.models.entities.analytics.EventsEntity
import com.luminoso.analytics.repositories.AgentRepository
import com.luminoso.analytics.repositories.AnalyticsRepository
import com.luminoso.analytics.repositories.EventsRepository
import com.luminoso.analytics.repositories.UserRepository
import com.luminoso.analytics.service.analytics.IAnalyticsService
import nl.basjes.parse.useragent.UserAgentAnalyzer
import org.springframework.boot.actuate.audit.AuditEventRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.security.Principal
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
class AnalyticsService(
    private val userAgentAnalyzer: UserAgentAnalyzer,
    private val userRepository: UserRepository,
    private val agentRepository: AgentRepository,
    private val eventsRepository: EventsRepository): IAnalyticsService {

    override fun postEvent(eventBody: EventBody, request: HttpServletRequest, principalId: String): Mono<EventResponse> {
        return getAgentEntity(request).map { agentEntity ->
            val eventsEntity = EventsEntity(eventBody.eventName,eventBody.value)

            val userEntity = userRepository.findByUuid(UUID.fromString(principalId))

            agentEntity.analytics = userEntity.analytics
//            agentEntity.addEvent(eventsEntity)

            agentRepository.save(agentEntity)


            EventResponse(eventsEntity.uuid)
        }.doOnSuccess {

        }
    }

    private fun getAgentEntity(request: HttpServletRequest): Mono<AgentEntity> {
        return Mono.fromCallable {
            val userAgent = userAgentAnalyzer.parse(request.getHeader("User-Agent")).toMap()
            AgentEntity(
                agentName = userAgent["AgentName"]!!,
                agentVersion = userAgent["AgentVersion"]!!,
                deviceBrand = userAgent["DeviceBrand"]!!,
                deviceClass = userAgent["DeviceClass"]!!,
                deviceName = userAgent["DeviceName"]!!,
                operatingSystemClass = userAgent["OperatingSystemClass"]!!,
                operatingSystemName = userAgent["OperatingSystemName"]!!,
                operatingSystemVersion = userAgent["OperatingSystemVersion"]!!
            )
        }.subscribeOn(Schedulers.elastic())
    }
}
