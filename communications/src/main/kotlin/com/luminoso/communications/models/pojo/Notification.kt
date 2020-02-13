package com.luminoso.communications.models.pojo

import java.util.*

data class Notification(
    val title: String,
    val topic: String,
    val body: String,
    // The notification's icon color, expressed in #rrggbb format.
    val color: String,
    val date: Date,
    // Time-To-Live.How long the message will be kept in FCM storage if the target devices are offline.
    val tTL: Long,
    // to-send or sent
    val status: String
)

