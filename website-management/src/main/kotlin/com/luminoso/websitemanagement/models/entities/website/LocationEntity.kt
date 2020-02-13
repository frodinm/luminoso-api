package com.luminoso.websitemanagement.models.entities.website

import com.luminoso.websitemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "opening_location")
data class LocationEntity(val city: String, val country: String): GeneratedIdBaseEntity() {

    @OneToMany(mappedBy = "location", fetch = FetchType.EAGER)
    var openings: MutableSet<OpeningEntity> = mutableSetOf()


}
