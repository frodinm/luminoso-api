package com.luminoso.messages.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Message(
    @Id
    var id: UUID = UUID.randomUUID(),
    var to: UUID? = null,
    var from: UUID? = null,
    var text: String = "",
    var tenant: String = ""
)