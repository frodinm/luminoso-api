package com.luminoso.tcs.controllers

import com.luminoso.tcs.services.TenantService
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class TenantController(private val tenantService: TenantService) {

    @GetMapping("/tenant/issuer")
    fun getTenantIssuer(@RequestParam("tenant_id") tenantId: String, serverHttpRequest: ServerHttpRequest): Mono<String> {
        return tenantService.tenantIssuerUri(tenantId)
    }

}