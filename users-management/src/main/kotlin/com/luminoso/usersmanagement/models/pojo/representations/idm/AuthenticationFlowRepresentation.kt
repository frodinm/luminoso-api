package com.luminoso.usersmanagement.models.pojo.representations.idm

import java.io.Serializable

data class AuthenticationFlowRepresentation(
    val id: String? = null,
    val alias: String? = null,
    val description: String? = null,
    val providerId: String? = null,
    val isTopLevel: Boolean = false,
    val isBuiltIn: Boolean = false,
    val authenticationExecutions: List<AuthenticationExecutionExportRepresentation>? = null
): Serializable