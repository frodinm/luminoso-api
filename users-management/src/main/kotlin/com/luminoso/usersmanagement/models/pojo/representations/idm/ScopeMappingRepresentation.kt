package com.luminoso.usersmanagement.models.pojo.representations.idm

data class ScopeMappingRepresentation(
    val self: String? = null,
    val client: String? = null,
    val clientScope: String? = null,
    val roles: MutableSet<String> = mutableSetOf()

) {
    fun role(role: String): ScopeMappingRepresentation {
        roles.add(role)
        return this
    }
}