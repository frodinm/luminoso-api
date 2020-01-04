package com.luminoso.usersmanagement.models.pojo.representations.idm

data class UserFederationProviderRepresentation(
    val id: String? = null,
    val displayName: String? = null,
    val providerName: String? = null,
    val config: Map<String, String>? = null,
    val priority: Int = 0,
    val fullSyncPeriod: Int = 0,
    val changedSyncPeriod: Int = 0,
    val lastSync: Int = 0
)