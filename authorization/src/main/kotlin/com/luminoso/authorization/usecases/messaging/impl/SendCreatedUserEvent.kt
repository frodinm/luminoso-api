package com.luminoso.authorization.usecases.messaging.impl

import com.luminoso.authorization.messaging.channels.UserCreatedChannel
import com.luminoso.authorization.models.messaging.UserCreatedPayload
import com.luminoso.authorization.models.pojo.User
import com.luminoso.authorization.usecases.messaging.ISendCreatedUserMessage
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class SendCreatedUserEvent(private val userCreatedChannel: UserCreatedChannel): ISendCreatedUserMessage {
    override fun send(user: User) {
        val userCreatedPayload = UserCreatedPayload(user.uuid)
        val userCreatedMessagePayload = MessageBuilder.withPayload(userCreatedPayload).build()
        userCreatedChannel.output().send(userCreatedMessagePayload)
    }
}
