package com.luminoso.tcs

import com.luminoso.tcs.model.messaging.TenantRealmChannel
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding

@EnableBinding(TenantRealmChannel::class)
@SpringBootApplication
class TcsApplication

fun main(args: Array<String>) {
    runApplication<TcsApplication>(*args)
}
