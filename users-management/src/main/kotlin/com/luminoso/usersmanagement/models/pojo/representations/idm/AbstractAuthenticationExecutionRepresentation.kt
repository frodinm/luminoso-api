package com.luminoso.usersmanagement.models.pojo.representations.idm

import java.io.Serializable

open class AbstractAuthenticationExecutionRepresentation(val authenticatorConfig: String? = null,
                                                         val authenticator: String? = null,
                                                         val isAutheticatorFlow: Boolean = false,
                                                         val requirement: String? = null,
                                                         val priority: Int = 0) : Serializable