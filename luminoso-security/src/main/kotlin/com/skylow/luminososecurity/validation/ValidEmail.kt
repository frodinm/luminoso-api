package com.skylow.luminososecurity.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.TYPE, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EmailValidator::class])
@MustBeDocumented
annotation class ValidEmail(val message: String = "Invalid email", val groups: Array<KClass<*>> = [], val payload: Array<KClass<out Payload>> = [])