package com.skylow.luminosogateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean

@EnableDiscoveryClient
@SpringBootApplication
class LuminosoGatewayApplication {

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route("resource") { r ->
                r.path("/resource")
                    .uri("lb://resource")
            }
            .build()
    }
}

fun main(args: Array<String>) {
    runApplication<LuminosoGatewayApplication>(*args)
}
