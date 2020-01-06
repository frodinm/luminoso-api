package com.luminoso.websitemanagement.repository

import com.luminoso.websitemanagement.models.entities.website.OpeningTasksEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OpeningTaskRepository: JpaRepository<OpeningTasksEntity,Int>