package com.luminoso.candidatemanagement.models.entities.candidate

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import com.luminoso.candidatemanagement.models.entities.user.UserEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "very_poor_candidate")
class VeryPoorCandidateEntity(): GeneratedIdBaseEntity() {


    val candidates: MutableSet<UserEntity> = mutableSetOf()
}