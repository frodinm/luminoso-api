package com.luminoso.communications.service.impl

import com.luminoso.communications.config.ApnsConfig
import com.luminoso.communications.models.pojo.Notification
import com.luminoso.communications.service.IIosNotificationService
import com.turo.pushy.apns.PushNotificationResponse
import com.turo.pushy.apns.util.ApnsPayloadBuilder
import com.turo.pushy.apns.util.SimpleApnsPushNotification
import com.turo.pushy.apns.util.TokenUtil
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class IosNotificationService(private val apnsConfig: ApnsConfig): IIosNotificationService {
    override fun sendToTopic(deviceToken: String, notification: Notification): Mono<PushNotificationResponse<SimpleApnsPushNotification>> {
        return Mono.fromCallable {
            val payload = ApnsPayloadBuilder()
                .setAlertBody(notification.body)
                .buildWithDefaultMaximumLength()

            val token = TokenUtil.sanitizeTokenString(deviceToken)

            val pushNotification = SimpleApnsPushNotification(token, notification.topic, payload)

            val apnsClient =  apnsConfig.apnsClient

            apnsClient.sendNotification(pushNotification).get()
        }.subscribeOn(Schedulers.elastic())
    }
}
