package com.luminoso.websitemanagement.models.entities.website

import com.luminoso.websitemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "company")
data class CompanyEntity(
    val tenantId: String,
    val name: String,
    val websiteUrl: String? = null,
    val logoUrl: String? = null
): GeneratedIdBaseEntity() {

//    @OneToOne(mappedBy = "company",fetch = FetchType.EAGER)
//    var website: WebsiteEntity? = null

//    @OneToOne(fetch = FetchType.EAGER)
//    var companyDescription: CompanyDescriptionEntity? = null

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL])
    val openings: MutableSet<OpeningEntity> = mutableSetOf()

    @OneToOne(mappedBy = "company", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var links: CompanySocialLinksEntity? = null

    fun addSocialLinks(links: CompanySocialLinksEntity) {
        this.links = links
        links.company = this
    }

    fun removeSocialLinks(links: CompanySocialLinksEntity) {
        this.links = null
        links.company = null
    }

    fun addOpening(opening: OpeningEntity) {
        openings.add(opening)
        opening.company = this
    }

    fun removeOpening(opening: OpeningEntity) {
        openings.remove(opening)
        opening.company = null
    }

//    fun addDescription(description: CompanyDescriptionEntity) {
//        companyDescription = description
//        description.company = this
//    }
//
//    fun removeDescription(description: CompanyDescriptionEntity) {
//        companyDescription = null
//        description.company = null
//    }
}