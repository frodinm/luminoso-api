package com.luminoso.tcs.services

import com.luminoso.tcs.repositories.ITenantIssuerRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class TenantService(private val tenantIssuerRepository: ITenantIssuerRepository) {
    fun tenantIssuerUri(tenantId: String): Mono<String> {
        return tenantIssuerRepository.findByTenantId(tenantId)
    }
}