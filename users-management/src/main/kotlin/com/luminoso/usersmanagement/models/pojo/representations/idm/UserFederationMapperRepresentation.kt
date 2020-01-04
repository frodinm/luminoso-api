package com.luminoso.usersmanagement.models.pojo.representations.idm

data class UserFederationMapperRepresentation(
    val id: String? = null,
    val name: String? = null,
    val federationProviderDisplayName: String? = null,
    val federationMapperType: String? = null,
    val config: Map<String, String>? = null
)