package com.luminoso.candidatemanagement.models.entities.interview

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "interview_guide")
class InterviewGuideEntity(val name: String): GeneratedIdBaseEntity() {

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(name = "interview_guide_sections", joinColumns = [JoinColumn(name = "interview_guide_id")], inverseJoinColumns = [JoinColumn(name = "section_id")])
    val sections: MutableSet<SectionEntity> = mutableSetOf()

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(name = "interview_guide_attachments", joinColumns = [JoinColumn(name = "interview_guide_id")], inverseJoinColumns = [JoinColumn(name = "attachment_id")])
    val attachments: MutableSet<AttachmentEntity> = mutableSetOf()


    fun addSection(section: SectionEntity) {
        this.sections.add(section)
        section.interviewGuides.add(this)
    }

    fun removeSection(section: SectionEntity){
        this.sections.remove(section)
        section.interviewGuides.remove(this)
    }

    fun addAttachments(attachment: AttachmentEntity) {
        this.attachments.add(attachment)
        attachment.interviewGuides.add(this)
    }

    fun removeAttachments(attachment: AttachmentEntity){
        this.attachments.remove(attachment)
        attachment.interviewGuides.remove(this)
    }
}