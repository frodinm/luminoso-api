package com.luminoso.tcs

import com.luminoso.tcs.model.messaging.TenantCreatedChannel
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding

@EnableBinding(TenantCreatedChannel::class)
@SpringBootApplication
class TcsApplication

fun main(args: Array<String>) {
    runApplication<TcsApplication>(*args)
}
