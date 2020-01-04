package com.luminoso.messages

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@EnableReactiveMongoRepositories
@SpringBootApplication
class MessagesApplication

fun main(args: Array<String>) {
    runApplication<MessagesApplication>(*args)
}
