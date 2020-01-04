package com.luminoso.usersmanagement.models.pojo.representations.idm

import com.fasterxml.jackson.annotation.JsonProperty
import com.luminoso.usersmanagement.models.pojo.representations.idm.authorization.ResourceServerRepresentation

class ClientRepresentation(
    val id: String? = null,
    val clientId: String? = null,
    val name: String? = null,
    val description: String? = null,
    val rootUrl: String? = null,
    val adminUrl: String? = null,
    val baseUrl: String? = null,
    @JsonProperty("surrogateAuthRequired")
    val isSurrogateAuthRequired: Boolean? = null,
    @JsonProperty("enabled")
    val isEnabled: Boolean? = null,
    val clientAuthenticatorType: String? = null,
    val secret: String? = null,
    val registrationAccessToken: String? = null,
    val defaultRoles: List<String>? = null,
    val redirectUris: List<String>? = null,
    val webOrigins: List<String>? = null,
    val notBefore: Int? = null,
    @JsonProperty("bearerOnly")
    val isBearerOnly: Boolean? = null,
    @JsonProperty("consentRequired")
    val isConsentRequired: Boolean? = null,
    @JsonProperty("standardFlowEnabled")
    val isStandardFlowEnabled: Boolean? = null,
    @JsonProperty("implicitFlowEnabled")
    val isImplicitFlowEnabled: Boolean? = null,
    @JsonProperty("directAccessGrantsEnabled")
    val isDirectAccessGrantsEnabled: Boolean? = null,
    @JsonProperty("serviceAccountsEnabled")
    val isServiceAccountsEnabled: Boolean? = null,
    val authorizationServicesEnabled: Boolean? = null,
    @JsonProperty("publicClient")
    val isPublicClient: Boolean? = null,
    @JsonProperty("frontchannelLogout")
    val isFrontchannelLogout: Boolean? = null,
    val protocol: String? = null,
    val attributes: Map<String, Any>? = null,
    val authenticationFlowBindingOverrides: Map<String, String>? = null,
    @JsonProperty("fullScopeAllowed")
    val isFullScopeAllowed: Boolean? = null,
    val nodeReRegistrationTimeout: Int? = null,
    val registeredNodes: Map<String, Int>? = null,
    val protocolMappers: List<ProtocolMapperRepresentation>? = null,
    val defaultClientScopes: List<String>? = null,
    val optionalClientScopes: List<String>? = null,
    val authorizationSettings: ResourceServerRepresentation? = null,
    val access: Map<String, Boolean>? = null,
    /**
     * Returns id of ClientStorageProvider that loaded this user
     *
     * @return NULL if user stored locally
     */
    val origin: String? = null
)