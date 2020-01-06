package com.luminoso.usersmanagement.models.messaging

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel

interface TenantCreatedChannel {

    companion object {
        const val OUTPUT = "tenant.created"
    }

    /**
     * @return output channel
     */
    @Output(OUTPUT)
    fun output(): MessageChannel
}