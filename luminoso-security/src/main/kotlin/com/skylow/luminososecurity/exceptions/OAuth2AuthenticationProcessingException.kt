package com.skylow.luminososecurity.exceptions

import java.lang.RuntimeException

class OAuth2AuthenticationProcessingException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)