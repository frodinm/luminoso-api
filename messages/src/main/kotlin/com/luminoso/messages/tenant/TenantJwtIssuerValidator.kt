package com.luminoso.messages.tenant

import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtIssuerValidator
import org.springframework.stereotype.Component
import java.util.*

@Component
class TenantJwtIssuerValidator : OAuth2TokenValidator<Jwt> {
    private val tenants: MutableMap<String, String> = HashMap()
    private val validators: MutableMap<String, JwtIssuerValidator> = HashMap()
    override fun validate(token: Jwt): OAuth2TokenValidatorResult {
        return validators.computeIfAbsent(toTenant(token)) { tenant: String -> fromTenant(tenant) }
            .validate(token)
    }

    private fun toTenant(jwt: Jwt): String {
        return jwt.getClaim("tenant_id")
    }

    private fun fromTenant(tenant: String): JwtIssuerValidator {
        return Optional.ofNullable(tenants[tenant])
            .map { issuer: String? -> JwtIssuerValidator(issuer) }
            .orElseThrow { IllegalArgumentException("unknown tenant") }
    }

    init {
        tenants["soullabs"] = "http://idp:8080/auth/realms/soullabs"
        tenants["client1"] = "http://idp:8080/auth/realms/client1"
    }
}