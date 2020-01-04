package com.luminoso.candidatemanagement.models.entities.candidate

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "candidate_tags")
data class TagsEntity(val value: String): GeneratedIdBaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    var candidate: CandidateEntity? = null
}