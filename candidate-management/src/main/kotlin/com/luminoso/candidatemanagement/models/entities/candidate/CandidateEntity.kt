package com.luminoso.candidatemanagement.models.entities.candidate

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import com.luminoso.candidatemanagement.models.entities.questionnaire.QuestionnaireEntity
import com.luminoso.candidatemanagement.models.entities.user.UserEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "candidate")
data class CandidateEntity(
    val address: String,
    val coverLetter: String,
    val headline: String,
    val origin: String, // "applied | recruiter | referral | sourced"
    val profile_photo_url: String,
    val summary: String,
    val dispositionDate: LocalDateTime? = null
): GeneratedIdBaseEntity() {

    @OneToOne(mappedBy = "candidate", cascade = [CascadeType.ALL],fetch = FetchType.LAZY)
    var questionnaire: QuestionnaireEntity? = null

    lateinit var overall_score: OverallScoreEntity

    @OneToOne(mappedBy = "candidate", cascade = [CascadeType.ALL],fetch = FetchType.LAZY)
    var assignedTo: UserEntity? = null

    @OneToMany(mappedBy = "candidate", cascade = [CascadeType.ALL])
    val education: MutableSet<EducationEntity> = mutableSetOf()

    var recruited_by: UserEntity? = null

    var referred_by: UserEntity? = null

    var sourced_by: UserEntity? = null

    @OneToOne(mappedBy = "candidate", cascade = [CascadeType.ALL],fetch = FetchType.LAZY)
    var resume: ResumeEntity? = null

    @OneToOne(mappedBy = "candidate", cascade = [CascadeType.ALL],fetch = FetchType.LAZY)
    var source: SourceEntity? = null

    @OneToOne(mappedBy = "candidate", cascade = [CascadeType.ALL],fetch = FetchType.LAZY)
    var stage: StageEntity? = null

    @OneToMany(mappedBy = "candidate", cascade = [CascadeType.ALL])
    val tags: MutableSet<TagsEntity> = mutableSetOf()

    @OneToMany(mappedBy = "candidate", cascade = [CascadeType.ALL])
    val workHistory: MutableSet<WorkHistoryEntity> = mutableSetOf()

    @OneToMany(mappedBy = "candidate", cascade = [CascadeType.ALL])
    val customAttributes: MutableSet<CustomAttributesEntity> = mutableSetOf()

    @OneToOne(mappedBy = "candidate", cascade = [CascadeType.ALL],fetch = FetchType.LAZY)
    var dispositionReason: DispositionReasonEntity? = null

    fun assignTo(user: UserEntity) {
        this.assignedTo = user
        user.candidate = this
    }

    fun unAssignFrom(user: UserEntity){
        this.assignedTo = null
        user.candidate = null
    }

    fun addEducation(education: EducationEntity) {
        this.education.add(education)
        education.candidate = this
    }

    fun removeEducation(education: EducationEntity){
        this.education.remove(education)
        education.candidate = null
    }

    fun addTag(tag: TagsEntity) {
        this.tags.add(tag)
        tag.candidate = this
    }

    fun removeTag(tag: TagsEntity){
        this.tags.remove(tag)
        tag.candidate = null
    }

    fun addResume(resume: ResumeEntity) {
        this.resume = resume
        resume.candidate = this
    }

    fun removeResume(resume: ResumeEntity){
        this.assignedTo = null
        resume.candidate = null
    }

    fun addSource(source: SourceEntity) {
        this.source = source
        source.candidate = this
    }

    fun removeSource(source: SourceEntity){
        this.source = null
        source.candidate = null
    }

    fun addStage(stage: StageEntity) {
        this.stage = stage
        stage.candidate = this
    }

    fun removeSource(stage: StageEntity){
        this.stage = null
        stage.candidate = null
    }

    fun addWorkHistory(workHistory: WorkHistoryEntity) {
        this.workHistory.add(workHistory)
        workHistory.candidate = this
    }

    fun removeWorkHistory(workHistory: WorkHistoryEntity){
        this.workHistory.remove(workHistory)
        workHistory.candidate = null
    }

    fun addCustomAttribute(customAttr: CustomAttributesEntity) {
        this.customAttributes.add(customAttr)
        customAttr.candidate = this
    }

    fun removeWorkHistory(customAttr: CustomAttributesEntity){
        this.customAttributes.remove(customAttr)
        customAttr.candidate = null
    }

    fun addDispositionReason(dispositionReason: DispositionReasonEntity) {
        this.dispositionReason = dispositionReason
        dispositionReason.candidate = this
    }

    fun removeDispositionReason(dispositionReason: DispositionReasonEntity){
        this.dispositionReason = null
        dispositionReason.candidate = null
    }
}