package com.luminoso.websitemanagement.models.entities.website

import com.luminoso.websitemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "website_banner")
data class  WebsiteBannerEntity(val title: String, val description: String): GeneratedIdBaseEntity() {

    @OneToOne(mappedBy = "banner", fetch = FetchType.EAGER)
    var website: WebsiteEntity? = null

}
