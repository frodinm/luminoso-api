package com.luminoso.websitemanagement.repository

import com.luminoso.websitemanagement.models.entities.website.OpeningSkillsEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OpeningSkillsRepository: JpaRepository<OpeningSkillsEntity,Int>