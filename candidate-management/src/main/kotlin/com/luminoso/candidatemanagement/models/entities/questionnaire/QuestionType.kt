package com.luminoso.candidatemanagement.models.entities.questionnaire

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "questionnaire_question_type")
data class QuestionType(val name: String): GeneratedIdBaseEntity() {

    @OneToMany(mappedBy = "type", cascade = [CascadeType.ALL], orphanRemoval = true)
    val questions: MutableSet<QuestionEntity> = mutableSetOf()

    fun addQuestionToType(question: QuestionEntity) {
        this.questions.add(question)
        question.type = this
    }

    fun removeQuestionToType(question: QuestionEntity){
        this.questions.remove(question)
        question.type = null
    }
}