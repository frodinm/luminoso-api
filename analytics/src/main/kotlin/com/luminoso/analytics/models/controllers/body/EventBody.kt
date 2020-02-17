package com.luminoso.analytics.models.controllers.body

import com.fasterxml.jackson.annotation.JsonProperty

data class EventBody(
    @JsonProperty("event_name")
    val eventName: String,
    @JsonProperty("value")
    val value: String
)
