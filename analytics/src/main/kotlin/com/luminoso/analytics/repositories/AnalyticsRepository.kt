package com.luminoso.analytics.repositories

import com.luminoso.analytics.models.entities.analytics.AnalyticsEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnalyticsRepository : JpaRepository<AnalyticsEntity, Int>
