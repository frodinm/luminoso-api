package com.luminoso.usersmanagement.models.pojo.representations.idm


data class AuthenticationExecutionExportRepresentation(val flowAlias: String? = null,
                                                       val isUserSetupAllowed: Boolean = false): AbstractAuthenticationExecutionRepresentation()