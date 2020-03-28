package com.luminoso.commonjpa.entities.security.extensions

import com.luminoso.apiauthorization.core.security.resource.OAuth2ApiKeyResourceServerConfigurer
import org.springframework.context.ApplicationContext
import org.springframework.security.config.annotation.web.builders.HttpSecurity

fun HttpSecurity.oauth2ApiKeyResourceServer(applicationContext: ApplicationContext): HttpSecurity {
    val configurer = OAuth2ApiKeyResourceServerConfigurer<HttpSecurity>(applicationContext)
    apply(configurer)
    return this
}

