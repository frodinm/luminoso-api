package com.luminoso.messages.tenant

import com.nimbusds.jwt.JWTParser
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.*

@Component
class TenantAuthenticationManagerResolver : ReactiveAuthenticationManagerResolver<ServerHttpRequest> {
    private val resolver = ReactiveDefaultBearerTokenResolver()
    private val tenants: MutableMap<String?, String?> = HashMap()
    private val authenticationManagers: MutableMap<String, ReactiveAuthenticationManager> = HashMap()

    override fun resolve(request: ServerHttpRequest): Mono<ReactiveAuthenticationManager>? {
        return authenticationManagers.computeIfAbsent(toTenant(request)) { tenant ->
            fromTenant(tenant)
        }.toMono()
    }

    private fun toTenant(request: ServerHttpRequest): String {
        return try {
            val token = resolver.resolve(request)
            JWTParser.parse(token).jwtClaimsSet.getClaim("tenant_id") as String
        } catch (e: Exception) {
            throw IllegalArgumentException(e)
        }
    }

    private fun fromTenant(tenant: String): ReactiveAuthenticationManager {
        return ReactiveAuthenticationManager { authentication: Authentication? ->
            Mono.fromCallable {
                try {
                    val issuer = tenants[tenant]
                    val jwtDecoder = JwtDecoders.fromIssuerLocation(issuer)
                    val jwtAuthenticationProvider = JwtAuthenticationProvider(jwtDecoder)
                    jwtAuthenticationProvider.authenticate(authentication)
                } catch (e: Exception) {
                    throw IllegalArgumentException("unknown tenant")
                }
            }
        }
    }

    @KafkaListener(topics = ["tenants"])
    fun action(action: Map<String?, Map<String?, Any?>?>) {
        if (action.containsKey("created")) {
            val tenant: Map<String?, Any?>? = action["created"]
            val alias = tenant!!["alias"] as String?
            val issuerUri = tenant["issuerUri"] as String?
            tenants[alias] = issuerUri
        }
    }

    init {
        tenants["soullabs"] = "http://idp:8080/auth/realms/soullabs"
        tenants["client1"] = "http://idp:8080/auth/realms/client1"
        tenants["client2"] = "http://idp:8080/auth/realms/client2"
    }
}