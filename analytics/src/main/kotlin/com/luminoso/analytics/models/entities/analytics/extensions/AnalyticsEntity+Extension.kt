package com.luminoso.analytics.models.entities.analytics.extensions

import com.luminoso.analytics.models.entities.analytics.AgentEntity
import com.luminoso.analytics.models.entities.analytics.AnalyticsEntity
import com.luminoso.analytics.models.entities.user.extensions.toDbEntity
import com.luminoso.analytics.models.entities.user.extensions.toExternal
import com.luminoso.analytics.models.pojo.analytics.Agent
import com.luminoso.analytics.models.pojo.analytics.Analytics

fun AnalyticsEntity.toExternal(): Analytics {
    val externalAgents = mutableSetOf<Agent>()

    for(agent in agents) {
        externalAgents.add(agent.toExternal())
    }

    return Analytics(analyticsUser!!.toExternal(),externalAgents)
}

fun Analytics.toDbEntity(): AnalyticsEntity {
    val dbAgents = mutableSetOf<AgentEntity>()

    for(agent in agents) {
        dbAgents.add(agent.toDbEntity())
    }
    val entity = AnalyticsEntity()
    entity.analyticsUser = analyticsUser.toDbEntity()
    entity.agents.addAll(dbAgents)
    return entity
}
