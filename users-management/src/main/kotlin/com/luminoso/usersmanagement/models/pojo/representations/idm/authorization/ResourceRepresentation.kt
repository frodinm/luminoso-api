package com.luminoso.usersmanagement.models.pojo.representations.idm.authorization

/**
 *
 * One or more resources that the resource server manages as a set of protected resources.
 *
 *
 * For more details, [OAuth-resource-reg](https://docs.kantarainitiative.org/uma/draft-oauth-resource-reg.html#rfc.section.2.2).
 *
 */
class ResourceRepresentation(
    val id: String? = null,
    val name: String? = null,
    val uris: Set<String> = mutableSetOf(),
    val type: String? = null,
    val scopes: MutableSet<ScopeRepresentation> = mutableSetOf(),
    val iconUri: String? = null,
    var owner: ResourceOwnerRepresentation? = null,
    val ownerManagedAccess: Boolean? = null,
    val displayName: String? = null,
    val attributes: Map<String, List<String>>? = null
) {

    fun setOwner(ownerId: String?) {
        if (ownerId == null) {
            return
        }
        if (owner == null) {
            owner = ResourceOwnerRepresentation(ownerId)
        }
    }

    fun addScope(vararg scopeNames: String?) {
        for (scopeName in scopeNames) {
            scopes.add(ScopeRepresentation(scopeName))
        }
    }
}