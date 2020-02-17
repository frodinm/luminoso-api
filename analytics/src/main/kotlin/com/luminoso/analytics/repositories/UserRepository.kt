package com.luminoso.analytics.repositories

import com.luminoso.analytics.models.entities.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, Int> {
    fun findByUuid(id: UUID): UserEntity
}

