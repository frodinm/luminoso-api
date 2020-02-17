package com.luminoso.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.server.WebFilter
import reactor.core.publisher.Mono
import java.net.URI
import java.net.URISyntaxException


@EnableDiscoveryClient
@SpringBootApplication
class GatewayApplication {

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route { r ->
                r.path("/authorization/**")
                    .filters { f -> f.rewritePath("/authorization/(?<remaining>.*)", "/\${remaining}")}
                    .uri("lb://authorization")
            }.route { r ->
                r.path("/analytics/**")
                    .filters { f -> f.rewritePath("/analytics/(?<remaining>.*)", "/\${remaining}")}
                    .uri("lb://analytics")
            }.build()
    }
}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}
