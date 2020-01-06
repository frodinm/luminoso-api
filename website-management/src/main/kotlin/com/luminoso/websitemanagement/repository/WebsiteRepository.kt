package com.luminoso.websitemanagement.repository

import com.luminoso.websitemanagement.models.entities.website.WebsiteEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WebsiteRepository: JpaRepository<WebsiteEntity,Int>