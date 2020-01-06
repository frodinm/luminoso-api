package com.luminoso.websitemanagement

import com.luminoso.websitemanagement.messaging.channels.TenantCreatedChannel
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding

@EnableBinding(TenantCreatedChannel::class)
@SpringBootApplication
class WebsiteManagementApplication

fun main(args: Array<String>) {
    runApplication<WebsiteManagementApplication>(*args)
}
