package com.luminoso.analytics.models.entities.analytics

import com.luminoso.analytics.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "agents")
data class AgentEntity(
    val agentName: String,
    val agentVersion: String,
    val deviceBrand: String,
    val deviceClass: String,
    val deviceName: String,
    val operatingSystemClass: String,
    val operatingSystemName: String,
    val operatingSystemVersion: String
): GeneratedIdBaseEntity() {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "analytics_id")
    var analytics: AnalyticsEntity? = null

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "agent_events", joinColumns = [JoinColumn(name = "agent_id")], inverseJoinColumns = [JoinColumn(name = "event_id")])
    val events: MutableSet<EventsEntity> = mutableSetOf()

    fun addEvent(event: EventsEntity) {
        events.add(event)
        event.agents.add(this)
    }

    fun removeEvent(event: EventsEntity) {
        events.remove(event)
        event.agents.remove(this)
    }

}
