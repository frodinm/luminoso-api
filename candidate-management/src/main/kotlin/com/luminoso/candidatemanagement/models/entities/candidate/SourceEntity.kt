package com.luminoso.candidatemanagement.models.entities.candidate

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "candidate_source")
data class SourceEntity(val name: String, val type: String): GeneratedIdBaseEntity(){

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    var candidate: CandidateEntity? = null
}