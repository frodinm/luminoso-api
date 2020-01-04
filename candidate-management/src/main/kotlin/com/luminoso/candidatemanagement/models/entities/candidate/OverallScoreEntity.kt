package com.luminoso.candidatemanagement.models.entities.candidate

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import com.luminoso.candidatemanagement.models.entities.user.UserEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "candidate_overall_score")
data class OverallScoreEntity(val scored_count: Int,
                              val score: Int): GeneratedIdBaseEntity() {

    val veryGood: MutableSet<UserEntity> = mutableSetOf()
    val neutral: MutableSet<UserEntity> = mutableSetOf()
    val veryPoor: MutableSet<UserEntity> = mutableSetOf()


}