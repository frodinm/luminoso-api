package com.luminoso.analytics.models.entities.analytics.extensions

import com.luminoso.analytics.models.entities.analytics.AgentEntity
import com.luminoso.analytics.models.entities.analytics.EventsEntity
import com.luminoso.analytics.models.pojo.analytics.Agent
import com.luminoso.analytics.models.pojo.analytics.Events

fun EventsEntity.toExternal(): Events {
    val externalAgents = mutableSetOf<Agent>()
    return Events(name,value,externalAgents)
}

fun Events.toDbEntity(): EventsEntity {
    val dbAgents = mutableSetOf<AgentEntity>()
    for(agent in agents) {
        dbAgents.add(agent.toDbEntity())
    }
    return EventsEntity(name,value)
}
