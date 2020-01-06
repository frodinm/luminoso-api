package com.luminoso.websitemanagement.models.entities.website

import com.luminoso.websitemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "opening_task")
data class OpeningTasksEntity(val task: String): GeneratedIdBaseEntity(){

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "opening_id")
    var opening: OpeningEntity? = null
}
