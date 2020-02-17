package com.luminoso.analytics.models.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass
import javax.persistence.PreUpdate

/**
 * https://tomharrisonjr.com/uuid-or-guid-as-primary-keys-be-careful-7b2aa3dcb439
 * Summary:
 *
 * UUID should not be a primary key given that crud operations with string PK would have bad performance.
 * The recommended solution is to have a int or bigInt PK and use a uuid to expose data to the outside.
 * This keeps performance up while not exposing the primary key.
 */

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
@JsonIgnoreProperties(value = ["createdAt", "updatedAt"], allowGetters = true)
open class BaseEntity(
    @Column(name = "uuid", nullable = false)
    open var uuid: UUID = UUID.randomUUID(),
    @UpdateTimestamp
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, columnDefinition = "DATE")
    open var updatedAt: LocalDateTime = LocalDateTime.now(),
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATE")
    open var createdAt: LocalDateTime = LocalDateTime.now()
): Serializable {

    @PreUpdate
    fun preUpdate(){
        updatedAt = LocalDateTime.now()
    }

}
