package com.luminoso.messages.tenant

import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.KeySourceException
import com.nimbusds.jose.proc.JWSAlgorithmFamilyJWSKeySelector
import com.nimbusds.jose.proc.JWSKeySelector
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.proc.JWTClaimsSetAwareJWSKeySelector
import org.springframework.stereotype.Component
import java.net.URL
import java.security.Key
import java.util.*

@Component
class TenantJWSKeySelector : JWTClaimsSetAwareJWSKeySelector<SecurityContext> {
    private val tenants: MutableMap<String, String> = HashMap()
    private val selectors: MutableMap<String, JWSKeySelector<SecurityContext>> = HashMap()
    @Throws(KeySourceException::class)
    override fun selectKeys(jwsHeader: JWSHeader, jwtClaimsSet: JWTClaimsSet, securityContext: SecurityContext): List<Key?> {
        return selectors.computeIfAbsent(toTenant(jwtClaimsSet)) { tenant: String -> fromTenant(tenant) }
            .selectJWSKeys(jwsHeader, securityContext)
    }

    private fun toTenant(claimSet: JWTClaimsSet): String {
        return claimSet.getClaim("tenant_id") as String
    }

    private fun fromTenant(tenant: String): JWSKeySelector<SecurityContext> {
        return Optional.ofNullable(tenants[tenant])
            .map { uri: String -> fromUri(uri) }
            .orElseThrow { IllegalArgumentException("unknown tenant") }
    }

    private fun fromUri(uri: String): JWSKeySelector<SecurityContext> {
        return try {
            JWSAlgorithmFamilyJWSKeySelector.fromJWKSetURL(URL(uri))
        } catch (e: Exception) {
            throw IllegalArgumentException(e)
        }
    }

    init {
        tenants["soullabs"] = "http://idp:8080/auth/realms/soullabs/protocol/openid-connect/certs"
        tenants["client1"] = "http://idp:8080/auth/realms/client1/protocol/openid-connect/certs"
    }
}