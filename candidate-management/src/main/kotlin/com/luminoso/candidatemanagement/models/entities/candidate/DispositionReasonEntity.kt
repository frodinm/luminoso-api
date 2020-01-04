package com.luminoso.candidatemanagement.models.entities.candidate

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "candidate_disposition_reason")
data class DispositionReasonEntity(val reason: String): GeneratedIdBaseEntity() {
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    var candidate: CandidateEntity? = null
}