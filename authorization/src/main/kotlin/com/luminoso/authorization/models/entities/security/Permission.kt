package com.luminoso.authorization.models.entities.security

import com.luminoso.authorization.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "permission")
data class Permission(
    val name: String
) : GeneratedIdBaseEntity() {
    @ManyToMany(mappedBy = "permissions",fetch = FetchType.EAGER)
    val roles: Set<RoleEntity> = emptySet()
}