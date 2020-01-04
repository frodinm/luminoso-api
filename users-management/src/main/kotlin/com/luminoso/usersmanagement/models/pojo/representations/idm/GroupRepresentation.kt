package com.luminoso.usersmanagement.models.pojo.representations.idm

data class GroupRepresentation(
    var id: String? = null,
    var name: String? = null,
    var path: String? = null,
    var attributes: MutableMap<String, List<String>>? = null,
    var realmRoles: List<String>? = null,
    var clientRoles: Map<String, List<String>>? = null,
    var subGroups: List<GroupRepresentation>? = null,
    var access: Map<String, Boolean>? = null
) {

    fun singleAttribute(name: String, value: String): GroupRepresentation {
        if (attributes == null) attributes = HashMap()
        attributes!![name] = listOf(value)
        return this
    }

}