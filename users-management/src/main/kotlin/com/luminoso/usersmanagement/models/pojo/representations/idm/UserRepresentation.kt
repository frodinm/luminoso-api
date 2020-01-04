package com.luminoso.usersmanagement.models.pojo.representations.idm

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRepresentation(
    var self: String? = null,
    var id: String? = null,
    var origin: String? = null,
    var createdTimestamp: Long? = null,
    var username: String? = null,
    @JsonProperty("enabled")
    var isEnabled: Boolean? = null,
    @JsonProperty("emailVerified")
    var isEmailVerified: Boolean? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var federationLink: String? = null,
    var serviceAccountClientId: String? = null,
    var attributes: MutableMap<String, List<String>>? = null,
    var credentials: List<CredentialRepresentation>? = null,
    var disableableCredentialTypes: Set<String>? = null,
    var requiredActions: List<String>? = null,
    var federatedIdentities: List<FederatedIdentityRepresentation>? = null,
    var realmRoles: List<String>? = null,
    var clientRoles: Map<String, List<String>>? = null,
    var clientConsents: List<UserConsentRepresentation>? = null,
    var notBefore: Int? = null,
    var groups: List<String>? = null,
    var access: Map<String, Boolean>? = null
) {
    fun singleAttribute(name: String, value: String?): UserRepresentation {
        if (attributes == null) attributes = HashMap()
        attributes!![name] = if (value == null) ArrayList() else listOf(value)
        return this
    }

}