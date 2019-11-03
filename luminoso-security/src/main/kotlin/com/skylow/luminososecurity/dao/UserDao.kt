package com.skylow.luminososecurity.dao

import com.skylow.luminososecurity.models.security.user.AuthUserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserDao : JpaRepository<AuthUserEntity, Int> {

    @Query(value = "select * from \"users\"  where username = ?;", nativeQuery = true)
    fun findByUserName(@Param("userName") userName: String): AuthUserEntity?

    @Query(value = "select * from \"users\"  where email = ?;", nativeQuery = true)
    fun findByEmail(@Param("email") email: String): AuthUserEntity?

    fun existsByEmail(email: String): Boolean
}