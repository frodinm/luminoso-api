package com.luminoso.websitemanagement.models.entities.website

import com.luminoso.websitemanagement.models.entities.BaseEntity
import com.luminoso.websitemanagement.models.entities.GeneratedIdBaseEntity
import com.luminoso.websitemanagement.models.entities.NonGeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "company_social_links")
data class CompanySocialLinksEntity(
    val twitter: String = "",
    val facebook: String = "",
    val instagram: String = "",
    val linkedin: String = "",
    val customLink: String = ""
): NonGeneratedIdBaseEntity() {

    @MapsId
    @OneToOne(fetch = FetchType.EAGER)
    var company: CompanyEntity? = null
}