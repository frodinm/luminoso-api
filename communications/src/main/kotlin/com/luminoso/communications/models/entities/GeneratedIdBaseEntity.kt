package com.luminoso.candidatemanagement.models.entities

import javax.persistence.*

@MappedSuperclass
open class GeneratedIdBaseEntity: BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
}
