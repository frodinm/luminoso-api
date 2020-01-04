package com.luminoso.candidatemanagement.models.entities.pipeline

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "pipeline")
data class PipelineEntity(val name: String, val icon: String, val action_count: Int, val type: String): GeneratedIdBaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pipeline_group_id")
    var pipelineGroup: PipelineGroupEntity? = null

}