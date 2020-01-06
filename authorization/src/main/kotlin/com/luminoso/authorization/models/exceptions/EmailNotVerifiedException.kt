package com.luminoso.authorization.models.exceptions

import java.lang.RuntimeException

class EmailNotVerifiedException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)