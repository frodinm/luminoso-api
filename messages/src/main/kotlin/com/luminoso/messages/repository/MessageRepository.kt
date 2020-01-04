package com.luminoso.messages.repository

import com.luminoso.messages.model.Message
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MessageRepository : ReactiveMongoRepository<Message, UUID>