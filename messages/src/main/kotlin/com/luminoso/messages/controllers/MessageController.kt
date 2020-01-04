package com.luminoso.messages.controllers

import com.luminoso.messages.CurrentUserId
import com.luminoso.messages.model.Message
import com.luminoso.messages.repository.MessageRepository
import org.springframework.data.domain.Example
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/messages")
class MessageController(private val messages: MessageRepository) {
    @GetMapping("/list")
    fun all(): Flux<Message?> {
        return messages.findAll()
    }

    @GetMapping("/inbox")
    fun inbox(@CurrentUserId currentUserId: String?): Flux<Message> {
        val example = Message()
        example.to = UUID.fromString(currentUserId)
        return messages.findAll(Example.of(example))
    }

    @GetMapping("/{id}")
    fun read(@PathVariable id: UUID): Mono<Message?> {
        return messages.findById(id)
    }

    @PostMapping
    fun send(@CurrentUserId from: String?, message: Message): Mono<Message> {
        message.from = UUID.fromString(from)
        return messages.save(message)
    }

}