package com.luminoso.usersmanagement.models.pojo.representations.idm


data class ComponentExportRepresentation(
    val id: String? = null,
    val name: String? = null,
    val providerId: String? = null,
    val subType: String? = null,
    val subComponents: MutableMap<String, ComponentExportRepresentation> = mutableMapOf(),
    val config: MutableMap<String, String> = mutableMapOf()
)