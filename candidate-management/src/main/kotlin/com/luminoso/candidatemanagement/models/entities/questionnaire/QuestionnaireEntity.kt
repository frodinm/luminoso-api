package com.luminoso.candidatemanagement.models.entities.questionnaire

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import com.luminoso.candidatemanagement.models.entities.candidate.CandidateEntity
import javax.persistence.*

@Entity
@Table(name = "questionnaire")
class QuestionnaireEntity(val name: String): GeneratedIdBaseEntity(){

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    var candidate: CandidateEntity? = null

    @OneToMany(mappedBy = "questionnaire", cascade = [CascadeType.ALL])
    var questions: MutableSet<QuestionEntity> = mutableSetOf()

}