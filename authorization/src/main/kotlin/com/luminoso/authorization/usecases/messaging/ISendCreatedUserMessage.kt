package com.luminoso.authorization.usecases.messaging

import com.luminoso.authorization.models.pojo.User

interface ISendCreatedUserMessage {
    fun send(user: User)
}
