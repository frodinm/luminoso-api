package com.luminoso.analytics.models.pojo.analytics

import com.luminoso.analytics.models.pojo.user.User

data class Analytics(
    var analyticsUser: User,
    val agents: MutableSet<Agent>
)
