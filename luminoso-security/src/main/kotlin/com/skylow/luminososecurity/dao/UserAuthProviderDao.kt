package com.skylow.luminososecurity.dao

import com.skylow.luminososecurity.models.security.user.UserAuthProvider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAuthProviderDao : JpaRepository<UserAuthProvider, Long>