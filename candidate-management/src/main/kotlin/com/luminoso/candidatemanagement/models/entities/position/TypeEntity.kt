package com.luminoso.candidatemanagement.models.entities.position

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "position_type")
data class TypeEntity(val name: String): GeneratedIdBaseEntity() {

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    var position: PositionEntity? = null
}