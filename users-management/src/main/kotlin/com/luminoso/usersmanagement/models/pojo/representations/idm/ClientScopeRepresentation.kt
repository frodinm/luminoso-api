package com.luminoso.usersmanagement.models.pojo.representations.idm

data class ClientScopeRepresentation(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val protocol: String? = null,
    val attributes: Map<String, String>? = null,
    val protocolMappers: List<ProtocolMapperRepresentation>? = null
)