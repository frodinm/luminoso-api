package com.luminoso.authorization.models.entities

import com.luminoso.authorization.models.entities.BaseEntity
import javax.persistence.*

@MappedSuperclass
open class GeneratedIdBaseEntity: BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
}
