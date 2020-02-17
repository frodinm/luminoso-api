package com.luminoso.analytics.service.messaging.impl

import com.luminoso.analytics.messaging.UserCreatedChannel
import com.luminoso.analytics.models.entities.analytics.AnalyticsEntity
import com.luminoso.analytics.models.entities.user.UserEntity
import com.luminoso.analytics.models.messaging.UserCreatedPayload
import com.luminoso.analytics.models.pojo.analytics.Analytics
import com.luminoso.analytics.models.pojo.user.User
import com.luminoso.analytics.repositories.UserRepository
import com.luminoso.analytics.service.messaging.IUserCreatedService
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.stereotype.Service

@Service
class UserCreatedService(
    private val userRepository: UserRepository): IUserCreatedService {

    @StreamListener(UserCreatedChannel.INPUT)
    override fun subscribeToUserCreatedEvents(userCreatedPayload: UserCreatedPayload) {
        val analytics =  AnalyticsEntity()

        val user = UserEntity()
        user.uuid = userCreatedPayload.uuid

        user.analytics = analytics
        analytics.analyticsUser = user

        userRepository.save(user)
    }
}
