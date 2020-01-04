package com.luminoso.usersmanagement.tenant

import com.luminoso.usersmanagement.properties.EnvProperties
import com.nimbusds.jwt.JWTParser
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class TenantAuthenticationManagerResolver(
    private val env: EnvProperties,
    private val rest: WebClient
): ReactiveAuthenticationManagerResolver<ServerHttpRequest> {
    private val resolver = ReactiveDefaultBearerTokenResolver()

    override fun resolve(request: ServerHttpRequest): Mono<ReactiveAuthenticationManager> {
        return computeAuthenticationManagerForTenant(withTenantId(request)).toMono()
    }

    private fun withTenantId(request: ServerHttpRequest): String {
        return try {
            val token = resolver.resolve(request)
            JWTParser.parse(token).jwtClaimsSet.getClaim("tenant_id") as String
        } catch (e: Exception) {
            throw IllegalArgumentException(e)
        }
    }

    private fun computeAuthenticationManagerForTenant(tenantId: String): ReactiveAuthenticationManager {
        return ReactiveAuthenticationManager { authentication: Authentication? ->
            try {
                rest.get().uri("${env.environment.gateway}/tcs/tenant/issuer?tenant_id={tenant_id}", tenantId).retrieve().bodyToMono(String::class.java).map {
                    val jwtDecoder = JwtDecoders.fromIssuerLocation(it)
                    val jwtAuthenticationProvider = JwtAuthenticationProvider(jwtDecoder)
                    jwtAuthenticationProvider.authenticate(authentication)
                }
            } catch (e: Exception) {
                throw IllegalArgumentException("unknown tenant")
            }
        }
    }
}