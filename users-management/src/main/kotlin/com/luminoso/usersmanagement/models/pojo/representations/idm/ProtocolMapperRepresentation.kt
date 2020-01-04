package com.luminoso.usersmanagement.models.pojo.representations.idm

import com.fasterxml.jackson.annotation.JsonProperty

data class ProtocolMapperRepresentation(
    val id: String? = null,
    val name: String? = null,
    val protocol: String? = null,
    val protocolMapper: String? = null,
    @JsonProperty("consentRequired")
    val isConsentRequired: Boolean = false,
    val consentText: String? = null,
    val config: Map<String, Any> = HashMap()
)