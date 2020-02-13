package com.luminoso.authorization.messaging.channels

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.SubscribableChannel

interface UserCreatedChannel {

    companion object {
        const val OUTPUT = "user.created"
    }

    /**
     * @return input channel.
     */
    @Output(OUTPUT)
    fun output(): SubscribableChannel
}
