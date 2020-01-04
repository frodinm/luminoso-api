package com.luminoso.usersmanagement.models.pojo.representations.idm

data class RoleRepresentation(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val isComposite: Boolean = false,
    val composites: Composites? = null,
    val clientRole: Boolean? = null,
    val containerId: String? = null,
    val attributes: MutableMap<String, List<String>> = mutableMapOf()
) {
    class Composites {
        var realm: Set<String>? = null
        var client: Map<String, List<String>>? = null
    }

    fun singleAttribute(name: String, value: String): RoleRepresentation {
        attributes[name] = listOf(value)
        return this
    }
}