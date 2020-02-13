package com.luminoso.candidatemanagement.models.entities

import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class NonGeneratedIdBaseEntity(@Id var id: Int = 0) : BaseEntity()
