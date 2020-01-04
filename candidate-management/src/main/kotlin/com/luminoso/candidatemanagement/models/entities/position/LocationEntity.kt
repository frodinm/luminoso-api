package com.luminoso.candidatemanagement.models.entities.position

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.Table

@Table(name = "position_location")
data class LocationEntity(val city: String, val name: String, val isRemote: Boolean): GeneratedIdBaseEntity() {

    var country: CountryEntity? = null

    var state: StateEntity? = null

}