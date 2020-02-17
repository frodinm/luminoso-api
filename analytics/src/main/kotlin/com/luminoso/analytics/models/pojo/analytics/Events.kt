package com.luminoso.analytics.models.pojo.analytics

data class Events(
    val name: String,
    val value: String,
    val agents: Set<Agent>
)
