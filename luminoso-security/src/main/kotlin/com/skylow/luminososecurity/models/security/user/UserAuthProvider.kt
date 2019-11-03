package com.skylow.luminososecurity.models.security.user

import com.skylow.luminososecurity.enums.AuthProvider
import com.skylow.luminososecurity.enums.PostgreSQLEnumType
import com.skylow.luminososecurity.models.entities.GeneratedIdBaseEntity
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*

@Entity
@TypeDef(name = "authProvider", typeClass = PostgreSQLEnumType::class)
@Table(name = "user_auth_provider")
data class UserAuthProvider(
    @Enumerated(EnumType.STRING)
    @Type(type = "authProvider")
    val provider: AuthProvider) : GeneratedIdBaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var authUser: AuthUserEntity? = null
}