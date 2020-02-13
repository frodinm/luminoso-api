package com.luminoso.communications.service

import com.luminoso.communications.models.pojo.Notification
import reactor.core.publisher.Mono

interface IAndroidNotificationService {
    fun sendToTopic(notification: Notification): Mono<String>
}
