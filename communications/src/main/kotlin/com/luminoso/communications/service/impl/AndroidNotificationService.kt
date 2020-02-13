package com.luminoso.communications.service.impl

import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.AndroidNotification
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.luminoso.communications.models.pojo.Notification
import com.luminoso.communications.service.IAndroidNotificationService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class AndroidNotificationService: IAndroidNotificationService {
    override fun sendToTopic(notification: Notification): Mono<String> {
        return Mono.fromCallable {
            val messageBuilder = Message.builder()
            val configBuilder = AndroidConfig.builder()
            val notificationBuilder = AndroidNotification.builder()

            val androidNotification = notificationBuilder
                .setTitle(notification.title)
                .setBody(notification.body)
                .setColor(notification.color)
                .build()

            val androidConfig = configBuilder
                .setPriority(AndroidConfig.Priority.NORMAL)
                .setTtl(notification.tTL)
                .setNotification(androidNotification)
                .build()

            val message = messageBuilder
                .setAndroidConfig(androidConfig)
                .setTopic(notification.topic)
                .build()

            FirebaseMessaging.getInstance().send(message)
        }.subscribeOn(Schedulers.elastic())
    }
}
