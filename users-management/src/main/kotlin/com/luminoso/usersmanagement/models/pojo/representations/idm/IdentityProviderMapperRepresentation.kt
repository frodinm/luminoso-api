package com.luminoso.usersmanagement.models.pojo.representations.idm

data class IdentityProviderMapperRepresentation(
    val id: String? = null,
    val name: String? = null,
    val identityProviderAlias: String? = null,
    val identityProviderMapper: String? = null,
    val config: Map<String, String> = HashMap()
)