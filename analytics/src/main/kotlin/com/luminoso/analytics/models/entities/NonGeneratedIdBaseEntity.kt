package com.luminoso.analytics.models.entities

import com.luminoso.analytics.models.entities.BaseEntity
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class NonGeneratedIdBaseEntity(@Id var id: Int = 0) : BaseEntity()
