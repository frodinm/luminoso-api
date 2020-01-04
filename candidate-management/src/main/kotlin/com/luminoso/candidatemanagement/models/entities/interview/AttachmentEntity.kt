package com.luminoso.candidatemanagement.models.entities.interview

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "attachment")
data class AttachmentEntity(val status: String, val fileName: String, val url: String): GeneratedIdBaseEntity() {

    @ManyToMany(mappedBy = "attachments")
    val interviewGuides: MutableSet<InterviewGuideEntity> = mutableSetOf()
}