package com.luminoso.candidatemanagement.models.entities.position

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import com.luminoso.candidatemanagement.models.entities.PostgreSQLEnumType
import com.luminoso.candidatemanagement.models.entities.candidate.CustomAttributesEntity
import com.luminoso.candidatemanagement.models.entities.candidate.TagsEntity
import com.luminoso.candidatemanagement.models.entities.pipeline.PipelineEntity
import com.luminoso.candidatemanagement.models.entities.questionnaire.QuestionnaireEntity
import com.luminoso.candidatemanagement.models.entities.scorecard.ScoreCardEntity
import com.luminoso.candidatemanagement.models.entities.user.UserEntity
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*

@Entity
@TypeDef(name = "sqlEnum", typeClass = PostgreSQLEnumType::class)
@Table(name = "position")
data class PositionEntity(
    @Enumerated(EnumType.STRING)
    @Type(type = "sqlEnum")
    val state: PositionState,
    val name: String,
    val education: String,
    val department: String,
    val requisitionId: String,
    val description: String,
    @Enumerated(EnumType.STRING)
    @Type(type = "sqlEnum")
    val candidateType: PositionCandidateType
): GeneratedIdBaseEntity() {

    @OneToOne(mappedBy = "position", cascade = [CascadeType.ALL],fetch = FetchType.LAZY)
    var type: TypeEntity? = null

    var location: LocationEntity? = null

    var applicationForm: ApplicationFormConfig? = null

    var creator: UserEntity? = null

    var questionnaire: QuestionnaireEntity? = null

    var scoreCard: ScoreCardEntity? = null

    var allUsers: MutableSet<UserEntity> = mutableSetOf()

    var allAdmins: MutableSet<UserEntity> = mutableSetOf()

    var pipeline: PipelineEntity? = null

    var customAttributes: MutableSet<CustomAttributesEntity> = mutableSetOf()

    var tags: MutableSet<String> = mutableSetOf()


    fun addType(type: TypeEntity) {
        this.type = type
        type.position = this
    }

    fun removeType(type: TypeEntity){
        this.type = null
        type.position = null
    }
}