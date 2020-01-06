package com.luminoso.websitemanagement.models.entities.website

import com.luminoso.websitemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "company_description")
data class CompanyDescriptionEntity(
    val about: String = "",
    val problem: String = "",
    val solution: String = ""
): GeneratedIdBaseEntity() {

//    @MapsId
//    @OneToOne(fetch = FetchType.EAGER)
//    var company: CompanyEntity? = null

}