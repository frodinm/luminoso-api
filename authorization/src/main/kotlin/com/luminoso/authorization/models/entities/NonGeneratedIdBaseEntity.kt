package com.luminoso.authorization.models.entities

import com.luminoso.authorization.models.entities.BaseEntity
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class NonGeneratedIdBaseEntity(@Id var id: Int = 0) : BaseEntity()
