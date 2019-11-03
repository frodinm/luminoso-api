package com.skylow.luminososecurity.validation

import java.util.regex.Pattern
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class EmailValidator : ConstraintValidator<ValidEmail, String> {
    private var pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")

    override fun isValid(value: String, context: ConstraintValidatorContext?): Boolean {
        return validateEmail(value)
    }

    private fun validateEmail(email: String): Boolean {
        return pattern.matcher(email).matches()
    }
}