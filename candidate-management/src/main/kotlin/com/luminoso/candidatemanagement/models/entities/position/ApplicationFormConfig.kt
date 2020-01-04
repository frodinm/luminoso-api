package com.luminoso.candidatemanagement.models.entities.position

import com.luminoso.candidatemanagement.models.entities.GeneratedIdBaseEntity
import com.luminoso.candidatemanagement.models.entities.PostgreSQLEnumType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@TypeDef(name = "formInputState", typeClass = PostgreSQLEnumType::class)
@Table(name = "application_form_config")
data class ApplicationFormConfig(
    @Enumerated(EnumType.STRING)
    @Type(type = "formInputState")
    val name: FormInputState,
    @Enumerated(EnumType.STRING)
    @Type(type = "formInputState")
    val headline: FormInputState,
    @Enumerated(EnumType.STRING)
    @Type(type = "formInputState")
    val summary: FormInputState,
    @Enumerated(EnumType.STRING)
    @Type(type = "formInputState")
    val profile_photo: FormInputState,
    @Enumerated(EnumType.STRING)
    @Type(type = "formInputState")
    val address: FormInputState,
    @Enumerated(EnumType.STRING)
    @Type(type = "formInputState")
    val emailAddress: FormInputState,
    @Enumerated(EnumType.STRING)
    @Type(type = "formInputState")
    val phoneNumber: FormInputState,
    @Enumerated(EnumType.STRING)
    @Type(type = "formInputState")
    val resume: FormInputState,
    @Enumerated(EnumType.STRING)
    @Type(type = "formInputState")
    val workHistory: FormInputState,
    @Enumerated(EnumType.STRING)
    @Type(type = "formInputState")
    val education: FormInputState,
    @Enumerated(EnumType.STRING)
    @Type(type = "formInputState")
    val coverLetter: FormInputState
): GeneratedIdBaseEntity() {

}