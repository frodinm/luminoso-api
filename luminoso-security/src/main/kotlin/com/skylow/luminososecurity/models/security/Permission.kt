package com.skylow.luminososecurity.models.security

import com.skylow.luminososecurity.models.entities.GeneratedIdBaseEntity
import javax.persistence.*

@Entity
@Table(name = "permission")
data class Permission(
    val name: String
) : GeneratedIdBaseEntity() {
    @ManyToMany(mappedBy = "permissions",fetch = FetchType.EAGER)
    val roles: Set<Role> = emptySet()
}