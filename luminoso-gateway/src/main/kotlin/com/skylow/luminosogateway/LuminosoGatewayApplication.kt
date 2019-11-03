package com.skylow.luminosogateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean

@EnableDiscoveryClient
@SpringBootApplication
class LuminosoGatewayApplication

fun main(args: Array<String>) {
    runApplication<LuminosoGatewayApplication>(*args)
}
