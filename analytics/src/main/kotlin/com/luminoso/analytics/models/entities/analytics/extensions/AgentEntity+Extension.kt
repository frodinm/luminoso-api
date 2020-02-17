package com.luminoso.analytics.models.entities.analytics.extensions

import com.luminoso.analytics.models.entities.analytics.AgentEntity
import com.luminoso.analytics.models.entities.analytics.EventsEntity
import com.luminoso.analytics.models.pojo.analytics.Agent
import com.luminoso.analytics.models.pojo.analytics.Events

fun AgentEntity.toExternal() : Agent {
    val externalEvents = mutableSetOf<Events>()
    for(event in events){
        externalEvents.add(event.toExternal())
    }
    return Agent(
        agentName,
        agentVersion,
        deviceBrand,
        deviceClass,
        deviceName,
        operatingSystemClass,
        operatingSystemName,
        operatingSystemVersion,
        analytics!!.toExternal(),
        externalEvents
    )
}

fun Agent.toDbEntity() : AgentEntity {
    val dbEvents = mutableSetOf<EventsEntity>()
    for(event in events){
        dbEvents.add(event.toDbEntity())
    }
    val entity = AgentEntity(
        agentName,
        agentVersion,
        deviceBrand,
        deviceClass,
        deviceName,
        operatingSystemClass,
        operatingSystemName,
        operatingSystemVersion
    )
    entity.analytics = analytics.toDbEntity()
    entity.events.addAll(dbEvents)
    return entity
}
