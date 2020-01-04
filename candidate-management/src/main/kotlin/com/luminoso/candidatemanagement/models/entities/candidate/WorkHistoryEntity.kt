package com.luminoso.candidatemanagement.models.entities.candidate

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "candidate_work_history")
data class WorkHistoryEntity(val companyName: String,
                        val title: String,
                        val summary: String,
                        val isCurrent: Boolean,
                        val startDate: WorkHistoryDate,
                        val endDate: WorkHistoryDate): GeneratedIdBaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    var candidate: CandidateEntity? = null
}


data class WorkHistoryDate(val month: Int, val year: Int)