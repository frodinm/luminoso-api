package com.luminoso.candidatemanagement.models.entities.interview

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "section")
data class SectionEntity(val type: String, val criteria: String, val name: String): GeneratedIdBaseEntity() {

    @ManyToMany(mappedBy = "sections")
    val interviewGuides: MutableSet<InterviewGuideEntity> = mutableSetOf()
}