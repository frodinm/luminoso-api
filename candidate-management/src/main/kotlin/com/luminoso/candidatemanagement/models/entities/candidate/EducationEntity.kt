package com.luminoso.candidatemanagement.models.entities.candidate

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "candidate_education")
data class EducationEntity(
    val schoolName: String,
    val fieldOfStudy: String,
    val degree: String,
    val isCurrent: Boolean
): GeneratedIdBaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    var candidate: CandidateEntity? = null
}