package com.luminoso.analytics.repositories

import com.luminoso.analytics.models.entities.analytics.EventsEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventsRepository : JpaRepository<EventsEntity, Int>
