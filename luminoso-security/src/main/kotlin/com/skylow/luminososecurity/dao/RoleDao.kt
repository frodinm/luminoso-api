package com.skylow.luminososecurity.dao

import com.skylow.luminososecurity.models.security.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleDao : JpaRepository<Role, Long> {
    fun findByName(name: String): Role
}