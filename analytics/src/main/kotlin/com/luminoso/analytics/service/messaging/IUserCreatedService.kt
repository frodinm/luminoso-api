package com.luminoso.analytics.service.messaging

import com.luminoso.analytics.models.messaging.UserCreatedPayload

interface IUserCreatedService {
    fun subscribeToUserCreatedEvents(userCreatedPayload: UserCreatedPayload)
}
