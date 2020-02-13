package com.luminoso.authorization.usecases.messaging.impl

import com.luminoso.authorization.messaging.channels.UserCreatedChannel
import com.luminoso.authorization.models.messaging.UserCreatedPayload
import com.luminoso.authorization.usecases.messaging.ISendCreatedUserMessage
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class SendCreatedUserMessage(private val userCreatedChannel: UserCreatedChannel): ISendCreatedUserMessage {
    override fun send() {
        val userCreatedPayload = UserCreatedPayload(name = "", email = "", verificationToken = "")
        val userCreatedMessagePayload = MessageBuilder.withPayload(userCreatedPayload).build()
        userCreatedChannel.output().send(userCreatedMessagePayload)
    }
}
