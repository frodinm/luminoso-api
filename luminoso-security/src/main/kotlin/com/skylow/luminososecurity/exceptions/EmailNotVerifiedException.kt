package com.skylow.luminososecurity.exceptions

import java.lang.RuntimeException

class EmailNotVerifiedException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)