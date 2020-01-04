package com.luminoso.usersmanagement.models.pojo.representations.idm

import java.util.*

data class IdentityProviderRepresentation(
    val alias: String? = null,
    val displayName: String? = null,
    val internalId: String? = null,
    val providerId: String? = null,
    val isEnabled: Boolean = true,
    val isTrustEmail: Boolean = false,
    val isStoreToken: Boolean = false,
    val isAddReadTokenRoleOnCreate: Boolean = false,
    val isLinkOnly: Boolean = false,
    val firstBrokerLoginFlowAlias: String? = null,
    val postBrokerLoginFlowAlias: String? = null,
    val config: Map<String, String> = HashMap()
)