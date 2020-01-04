package com.luminoso.tcs.repositories

import com.luminoso.tcs.model.messaging.Tenant
import reactor.core.publisher.Mono

interface ITenantIssuerRepository {
    fun save(tenant: Tenant): Mono<Boolean>
    fun findByTenantId(tenantId: String): Mono<String>
}