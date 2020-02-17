package com.luminoso.analytics

import com.luminoso.analytics.messaging.UserCreatedChannel
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding

@EnableBinding(UserCreatedChannel::class)
@SpringBootApplication
class AnalyticsApplication

fun main(args: Array<String>) {
    runApplication<AnalyticsApplication>(*args)
}
