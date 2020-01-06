package com.luminoso.websitemanagement.models.entities.website

import com.luminoso.websitemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "openings_departments")
data class DepartmentsEntity(val name: String): GeneratedIdBaseEntity() {

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    var openings: MutableSet<OpeningEntity> = mutableSetOf()
}