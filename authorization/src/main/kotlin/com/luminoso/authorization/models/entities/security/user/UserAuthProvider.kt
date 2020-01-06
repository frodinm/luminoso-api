package com.luminoso.authorization.models.entities.security.user

import com.luminoso.authorization.enums.AuthProvider
import com.luminoso.authorization.models.entities.GeneratedIdBaseEntity
import com.luminoso.authorization.models.entities.PostgreSQLEnumType
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
    var authUser: UserEntity? = null
}