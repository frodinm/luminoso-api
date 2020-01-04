package com.luminoso.candidatemanagement.models.entities.candidate

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "candidate_social_profiles")
data class SocialProfileEntity(val url: String): GeneratedIdBaseEntity() {

    @OneToOne(mappedBy = "candidate", cascade = [CascadeType.ALL],fetch = FetchType.LAZY)
    var socialProfileType: SocialProfileType? = null

    fun addSocialProfileType(type: SocialProfileType) {
        this.socialProfileType = type
        type.socialProfileEntity = this
    }

    fun removeSocialProfileType(type: SocialProfileType){
        this.socialProfileType = null
        type.socialProfileEntity = null
    }
}