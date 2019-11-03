package com.skylow.luminososecurity.exceptions

import java.lang.RuntimeException

class BadRequestException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)