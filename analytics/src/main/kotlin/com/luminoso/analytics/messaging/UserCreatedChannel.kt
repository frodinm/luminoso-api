package com.luminoso.analytics.messaging

import org.springframework.cloud.stream.annotation.Input
import org.springframework.messaging.SubscribableChannel

interface UserCreatedChannel {
    companion object {
        const val INPUT = "user.created"
    }

    /**
     * @return input channel.
     */
    @Input(INPUT)
    fun input(): SubscribableChannel
}
