package com.luminoso.authorization.repository

import com.luminoso.authorization.models.entities.security.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Int> {

    @Query(value = "select * from \"users\"  where username = ?;", nativeQuery = true)
    fun findByUserName(@Param("userName") userName: String): UserEntity?

    @Query(value = "select * from \"users\"  where email = ?;", nativeQuery = true)
    fun findByEmail(@Param("email") email: String): UserEntity?

    fun existsByEmail(email: String): Boolean
}