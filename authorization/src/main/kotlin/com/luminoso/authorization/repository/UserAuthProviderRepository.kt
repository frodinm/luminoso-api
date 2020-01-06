package com.luminoso.authorization.repository

import com.luminoso.authorization.models.entities.security.user.UserAuthProvider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAuthProviderRepository : JpaRepository<UserAuthProvider, Long>