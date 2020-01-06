package com.luminoso.websitemanagement.services.impl

import com.luminoso.websitemanagement.models.entities.website.*
import com.luminoso.websitemanagement.repository.CompanyDescriptionRepository
import com.luminoso.websitemanagement.repository.CompanyRepository
import com.luminoso.websitemanagement.repository.WebsiteRepository
import com.luminoso.websitemanagement.services.ICreateTenantWebsiteService
import org.springframework.stereotype.Service

@Service
class CreateTenantWebsiteService(
    private val repository: CompanyRepository
): ICreateTenantWebsiteService {

    override fun createWebsite(tenantId: String) {
        val companyEntity = CompanyEntity(tenantId,tenantId)

        val linksEntity = CompanySocialLinksEntity()
        val companyDescriptionEntity = CompanyDescriptionEntity()

        companyEntity.addSocialLinks(linksEntity)
//        companyEntity.addDescription(companyDescriptionEntity)

        val websiteBannerEntity = WebsiteBannerEntity("Lorem ipsum dolor sit amet, consectetur adipiscing elit","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Tincidunt id aliquet risus feugiat. Id cursus metus aliquam eleifend mi in. Habitant morbi tristique senectus et netus et. Elit sed vulputate mi sit amet mauris commodo. ")

        val websiteEntity = WebsiteEntity()
//        websiteEntity.linkCompany(companyEntity)
        websiteEntity.addBanner(websiteBannerEntity)

        repository.save(companyEntity)
    }
}