package com.luminoso.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean

@EnableDiscoveryClient
@SpringBootApplication
class GatewayApplication {

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route { r ->
                r.path("/candidate/**")
                    .filters { f -> f.rewritePath("/candidate/(?<remaining>.*)", "/candidate-management/\${remaining}")}
                    .uri("lb://candidate-management")
            }.route { r ->
                r.path("/tcs/**")
                    .filters { f -> f.rewritePath("/tcs/(?<remaining>.*)", "/\${remaining}")}
                    .uri("lb://tenant-context-service")
            }.route { r ->
                r.path("/signup")
                    .filters { f -> f.rewritePath("/signup/", "/users-management/signup")}
                    .uri("lb://users-management")
            }.route { r ->
                r.path("/image/**")
                    .filters { f -> f.rewritePath("/cdn/(?<remaining>.*)", "/\${remaining}")}
                    .uri("lb://cdn")

            }.build()
    }



}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}
