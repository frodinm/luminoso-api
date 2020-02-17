package com.luminoso.analytics.models.pojo.analytics

data class Agent(
    val agentName: String,
    val agentVersion: String,
    val deviceBrand: String,
    val deviceClass: String,
    val deviceName: String,
    val operatingSystemClass: String,
    val operatingSystemName: String,
    val operatingSystemVersion: String,
    val analytics: Analytics,
    val events: MutableSet<Events>
)
