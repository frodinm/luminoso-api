package com.luminoso.commonjpa.entities

import javax.persistence.*

@MappedSuperclass
open class GeneratedIdBaseEntity: BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
}
