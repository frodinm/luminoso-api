package com.luminoso.usersmanagement.models.pojo.representations.idm.authorization

data class ResourceServerRepresentation(
    val id: String? = null,
    val clientId: String? = null,
    val name: String? = null,
    val isAllowRemoteResourceManagement: Boolean = true,
    val policyEnforcementMode: String = PolicyEnforcementMode.ENFORCING.name,
    val resources: List<ResourceRepresentation> = emptyList(),
    val policies: List<PolicyRepresentation> = emptyList(),
    val scopes: List<ScopeRepresentation> = emptyList()
)