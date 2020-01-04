package com.luminoso.usersmanagement.models.pojo.representations.idm


data class RolesRepresentation(
    val realm: List<RoleRepresentation>? = null,
    val client: Map<String, List<RoleRepresentation>>? = null
)