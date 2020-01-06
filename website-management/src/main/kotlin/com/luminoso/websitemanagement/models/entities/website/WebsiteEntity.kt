package com.luminoso.websitemanagement.models.entities.website

import com.luminoso.websitemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.Entity
import javax.persistence.MapsId
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "website")
class WebsiteEntity: GeneratedIdBaseEntity() {

    @MapsId
    @OneToOne
    var company: CompanyEntity? = null

    @MapsId
    @OneToOne
    var banner: WebsiteBannerEntity? = null

//    fun linkCompany(company: CompanyEntity){
//        this.company = company
//        company.website = this
//    }
//
//    fun unlinkCompany(company: CompanyEntity){
//        this.company = null
//        company.website = null
//    }

    fun addBanner(banner: WebsiteBannerEntity){
        this.banner = banner
        banner.website = this
    }

    fun removeBanner(banner: WebsiteBannerEntity){
        this.banner = null
        banner.website = null
    }
}