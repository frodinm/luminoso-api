package com.luminoso.communications.config

import com.turo.pushy.apns.ApnsClient
import com.turo.pushy.apns.ApnsClientBuilder
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import javax.annotation.PostConstruct

@Configuration
class ApnsConfig(private val environment: Environment) {

    private lateinit var _apnsClient: ApnsClient
    val apnsClient: ApnsClient
        get() = _apnsClient

    @PostConstruct
    fun init() {
        val profile = environment.activeProfiles[0]

        val apnsServer = if(profile == "docker") {
            ApnsClientBuilder.PRODUCTION_APNS_HOST
        } else {
            ApnsClientBuilder.DEVELOPMENT_APNS_HOST
        }

        _apnsClient = ApnsClientBuilder()
            .setApnsServer(apnsServer)
//            .setClientCredentials() Requires membership to get certificate
            .build()
    }
}
