package com.luminoso.websitemanagement.repository

import com.luminoso.websitemanagement.models.entities.website.CompanyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyRepository: JpaRepository<CompanyEntity,Int>