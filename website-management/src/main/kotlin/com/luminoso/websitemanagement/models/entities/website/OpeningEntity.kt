package com.luminoso.websitemanagement.models.entities.website

import com.luminoso.websitemanagement.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "opening")
data class OpeningEntity(
    val positionTitle: String,
    val employmentStatus: String, // Full-time/part-time
    val role: String
): GeneratedIdBaseEntity() {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    var company: CompanyEntity? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    var location: LocationEntity? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    var department: DepartmentsEntity? = null

    @OneToMany(mappedBy = "opening", cascade = [CascadeType.ALL])
    val tasks: MutableSet<OpeningTasksEntity> = mutableSetOf()

    @OneToMany(mappedBy = "opening", cascade = [CascadeType.ALL])
    val skills: MutableSet<OpeningSkillsEntity> = mutableSetOf()

    fun addOpeningTask(task: OpeningTasksEntity) {
        this.tasks.add(task)
        task.opening = this
    }

    fun removeOpeningTask(task: OpeningTasksEntity){
        this.tasks.remove(task)
        task.opening = null
    }

    fun addOpeningSkill(skill: OpeningSkillsEntity) {
        this.skills.add(skill)
        skill.opening = this
    }

    fun removeOpeningSkill(skill: OpeningSkillsEntity){
        this.skills.remove(skill)
        skill.opening = null
    }

    fun addLocation(location: LocationEntity) {
        this.location = location
        location.openings.add(this)
    }

    fun removeLocation(location: LocationEntity){
        this.location = location
        location.openings.remove(this)
    }

    fun addDepartment(department: DepartmentsEntity) {
        this.department = department
        department.openings.add(this)
    }

    fun removeDepartment(department: DepartmentsEntity){
        this.department = department
        department.openings.remove(this)
    }
}