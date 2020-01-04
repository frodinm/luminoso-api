package com.luminoso.candidatemanagement.models.entities.questionnaire

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "questionnaire_questions")
data class QuestionEntity(val text: String,val required: Boolean, val response: String): GeneratedIdBaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_id")
    var questionnaire: QuestionnaireEntity? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    var type: QuestionType? = null


}