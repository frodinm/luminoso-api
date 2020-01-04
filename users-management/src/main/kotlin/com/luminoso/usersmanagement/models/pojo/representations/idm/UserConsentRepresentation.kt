package com.luminoso.usersmanagement.models.pojo.representations.idm


data class UserConsentRepresentation(
    val clientId: String? = null,
    val grantedClientScopes: List<String>? = null,
    val createdDate: Long? = null,
    val lastUpdatedDate: Long? = null
)