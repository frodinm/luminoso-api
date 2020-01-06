package com.luminoso.websitemanagement.repository

import com.luminoso.websitemanagement.models.entities.website.CompanyDescriptionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyDescriptionRepository: JpaRepository<CompanyDescriptionEntity,Int>