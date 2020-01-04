package com.luminoso.candidatemanagement.models.entities.position

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "country_state")
data class StateEntity(val name: String): GeneratedIdBaseEntity() {

}