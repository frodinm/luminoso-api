package com.luminoso.websitemanagement.repository

import com.luminoso.websitemanagement.models.entities.website.OpeningEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OpeningRepository: JpaRepository<OpeningEntity,Int>