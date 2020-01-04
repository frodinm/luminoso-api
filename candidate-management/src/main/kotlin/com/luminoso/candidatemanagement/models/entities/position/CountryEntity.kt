package com.luminoso.candidatemanagement.models.entities.position

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "country")
data class CountryEntity(val name: String): GeneratedIdBaseEntity() {
}