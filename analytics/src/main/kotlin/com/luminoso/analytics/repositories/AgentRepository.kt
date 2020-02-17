package com.luminoso.analytics.repositories

import com.luminoso.analytics.models.entities.analytics.AgentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AgentRepository : JpaRepository<AgentEntity, Int>
