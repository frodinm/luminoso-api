package com.luminoso.analytics.models.entities.analytics

import com.luminoso.analytics.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "events")
data class EventsEntity(
    val name: String,
    val value: String
): GeneratedIdBaseEntity(){

    @ManyToMany(mappedBy = "events",fetch = FetchType.LAZY)
    val agents: MutableSet<AgentEntity> = mutableSetOf()

}
