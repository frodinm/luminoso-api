package com.luminoso.websitemanagement.repository

import com.luminoso.websitemanagement.models.entities.website.CompanySocialLinksEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanySocialLinksRepository: JpaRepository<CompanySocialLinksEntity,Int>