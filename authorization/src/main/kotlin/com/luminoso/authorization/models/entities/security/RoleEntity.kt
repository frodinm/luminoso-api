package com.luminoso.authorization.models.entities.security

import com.luminoso.commonjpa.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "role")
data class RoleEntity(
    var name: String = ""
) : GeneratedIdBaseEntity() {
    @ManyToMany(cascade = [CascadeType.PERSIST,CascadeType.MERGE],fetch = FetchType.EAGER)
    @JoinTable(name = "permission_role", joinColumns = [JoinColumn(name = "role_id")], inverseJoinColumns = [JoinColumn(name = "permission_id")])
    val permissions: Set<Permission> = emptySet()
}
