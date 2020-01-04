package com.luminoso.candidatemanagement.models.entities.company

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "company")
data class CompanyEntity(val name: String, val memberCount: Int): GeneratedIdBaseEntity()