package com.luminoso.authorization.models.entities.organization

import com.luminoso.commonjpa.entities.GeneratedIdBaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "collaborators")
data class CollaboratorsEntity(
    val administrator: Boolean = false
): GeneratedIdBaseEntity() {
}
