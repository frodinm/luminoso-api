package com.luminoso.analytics.models.entities.analytics

import com.luminoso.analytics.models.entities.GeneratedIdBaseEntity
import com.luminoso.analytics.models.entities.user.UserEntity
import javax.persistence.*

@Entity
@Table(name = "analytics")
class AnalyticsEntity: GeneratedIdBaseEntity() {

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    var analyticsUser: UserEntity? = null

    @OneToMany(mappedBy = "analytics", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val agents: MutableSet<AgentEntity> = mutableSetOf()

    fun insertAgent(agent: AgentEntity) {
        this.agents.add(agent)
        agent.analytics = this
    }

    fun deleteAgent(agent: AgentEntity){
        this.agents.remove(agent)
        agent.analytics = null
    }

}
