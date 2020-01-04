package com.luminoso.tcs.repositories.impl

import com.luminoso.tcs.model.messaging.Tenant
import com.luminoso.tcs.repositories.ITenantIssuerRepository
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class RedisTenantIssuerRepository(
    private val operations: ReactiveStringRedisTemplate
): ITenantIssuerRepository {

    companion object {
        const val ISSUER_URI_POSTFIX = "_issuer_uri"
    }

    override fun save(tenant: Tenant): Mono<Boolean> {
       return operations.opsForValue().set(tenant.alias + ISSUER_URI_POSTFIX,tenant.issuerUri)
    }

    override fun findByTenantId(tenantId: String): Mono<String> {
       return operations.opsForValue().get(tenantId + ISSUER_URI_POSTFIX)
    }
}