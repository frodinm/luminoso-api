package com.luminoso.communications.service

import com.luminoso.communications.models.pojo.Notification
import com.turo.pushy.apns.PushNotificationResponse
import com.turo.pushy.apns.util.SimpleApnsPushNotification
import reactor.core.publisher.Mono

interface IIosNotificationService {
    fun sendToTopic(deviceToken: String, notification: Notification): Mono<PushNotificationResponse<SimpleApnsPushNotification>>
}
