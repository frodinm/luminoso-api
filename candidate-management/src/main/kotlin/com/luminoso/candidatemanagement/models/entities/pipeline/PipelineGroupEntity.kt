package com.luminoso.candidatemanagement.models.entities.pipeline

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "pipeline_group")
data class PipelineGroupEntity(val name: String, val type: String): GeneratedIdBaseEntity() {

    @OneToMany(mappedBy = "pipelineGroup", cascade = [CascadeType.ALL])
    val pipelines: MutableSet<PipelineEntity> = mutableSetOf()

    fun addPipeline(pipeline: PipelineEntity) {
        this.pipelines.add(pipeline)
        pipeline.pipelineGroup = this
    }

    fun removeAttachments(pipeline: PipelineEntity){
        this.pipelines.remove(pipeline)
        pipeline.pipelineGroup = null
    }
}