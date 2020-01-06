package com.luminoso.websitemanagement.repository

import com.luminoso.websitemanagement.models.entities.website.CompanyEntity
import com.luminoso.websitemanagement.models.entities.website.WebsiteBannerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WebsiteBannerRepository: JpaRepository<WebsiteBannerEntity,Int>