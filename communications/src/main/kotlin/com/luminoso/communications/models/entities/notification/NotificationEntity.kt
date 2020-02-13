package com.luminoso.communications.models.entities.notification

import com.luminoso.communications.models.entities.GeneratedIdBaseEntity
import com.luminoso.communications.models.entities.PostgreSQLEnumType
import com.luminoso.communications.models.enums.NotificationPlatform
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@TypeDef(name = "sqlEnum", typeClass = PostgreSQLEnumType::class)
@Table(name = "notification")
data class NotificationEntity(
    @Type(type = "sqlEnum")
    val platform: NotificationPlatform
): GeneratedIdBaseEntity() {

}
