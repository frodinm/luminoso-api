package com.skylow.luminososecurity.models.security

import com.skylow.luminososecurity.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "role")
data class Role(
    var name: String = ""
) : GeneratedIdBaseEntity() {
    @ManyToMany(cascade = [CascadeType.PERSIST,CascadeType.MERGE],fetch = FetchType.EAGER)
    @JoinTable(name = "permission_role", joinColumns = [JoinColumn(name = "role_id")], inverseJoinColumns = [JoinColumn(name = "permission_id")])
    val permissions: Set<Permission> = emptySet()
}