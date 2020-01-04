package com.luminoso.usersmanagement.models.pojo.representations.idm

data class FederatedIdentityRepresentation(
    val identityProvider: String? = null,
    val userId: String? = null,
    val userName: String? = null
)