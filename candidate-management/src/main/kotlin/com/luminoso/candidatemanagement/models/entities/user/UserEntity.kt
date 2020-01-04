package com.luminoso.candidatemanagement.models.entities.user

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import com.luminoso.candidatemanagement.models.entities.candidate.CandidateEntity
import javax.persistence.*

@Entity
@Table(name = "user")
data class UserEntity(
    val email_address: String,
    val name: String,
    val username: String,
    val verified_email: Boolean,
    val tz_offset: String
) : GeneratedIdBaseEntity() {

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    var candidate: CandidateEntity? = null
}