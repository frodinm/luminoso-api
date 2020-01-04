package com.luminoso.usersmanagement.models.pojo.representations.idm

import com.fasterxml.jackson.annotation.JsonProperty

data class RealmRepresentation(
    val id: String? = null,
    val realm: String? = null,
    val displayName: String? = null,
    val displayNameHtml: String? = null,
    val notBefore: Int? = null,
    val defaultSignatureAlgorithm: String? = null,
    val revokeRefreshToken: Boolean? = null,
    val refreshTokenMaxReuse: Int? = null,
    val accessTokenLifespan: Int? = null,
    val accessTokenLifespanForImplicitFlow: Int? = null,
    val ssoSessionIdleTimeout: Int? = null,
    val ssoSessionMaxLifespan: Int? = null,
    val ssoSessionIdleTimeoutRememberMe: Int? = null,
    val ssoSessionMaxLifespanRememberMe: Int? = null,
    val offlineSessionIdleTimeout: Int? = null,
    // KEYCLOAK-7688 Offline Session Max for Offline Token
    // KEYCLOAK-7688 Offline Session Max for Offline Token
    val offlineSessionMaxLifespanEnabled: Boolean? = null,
    val offlineSessionMaxLifespan: Int? = null,
    val accessCodeLifespan: Int? = null,
    val accessCodeLifespanUserAction: Int? = null,
    val accessCodeLifespanLogin: Int? = null,
    val actionTokenGeneratedByAdminLifespan: Int? = null,
    val actionTokenGeneratedByUserLifespan: Int? = null,
    @JsonProperty("enabled")
    val isEnabled: Boolean? = null,
    val sslRequired: String? = null,
    @JsonProperty("registrationAllowed")
    val isRegistrationAllowed: Boolean? = null,
    @JsonProperty("registrationEmailAsUsername")
    val isRegistrationEmailAsUsername: Boolean? = null,
    @JsonProperty("rememberMe")
    val isRememberMe: Boolean? = null,
    @JsonProperty("verifyEmail")
    val isVerifyEmail: Boolean? = null,
    @JsonProperty("loginWithEmailAllowed")
    val isLoginWithEmailAllowed: Boolean? = null,
    @JsonProperty("duplicateEmailsAllowed")
    val isDuplicateEmailsAllowed: Boolean? = null,
    @JsonProperty("resetPasswordAllowed")
    val isResetPasswordAllowed: Boolean? = null,
    @JsonProperty("editUsernameAllowed")
    val isEditUsernameAllowed: Boolean? = null,
    //--- brute force settings
    @JsonProperty("bruteForceProtected")
    val isBruteForceProtected: Boolean? = null,
    @JsonProperty("permanentLockout")
    val isPermanentLockout: Boolean? = null,
    val maxFailureWaitSeconds: Int? = null,
    val minimumQuickLoginWaitSeconds: Int? = null,
    val waitIncrementSeconds: Int? = null,
    val quickLoginCheckMilliSeconds: Long? = null,
    val maxDeltaTimeSeconds: Int? = null,
    val failureFactor: Int? = null,
    //--- end brute force settings
    val roles: RolesRepresentation? = null,
    val groups: List<GroupRepresentation>? = null,
    val defaultRoles: List<String>? = null,
    val defaultGroups: List<String>? = null,
    val passwordPolicy: String? = null,
    val otpPolicyType: String? = null,
    val otpPolicyAlgorithm: String? = null,
    val otpPolicyInitialCounter: Int? = null,
    val otpPolicyDigits: Int? = null,
    val otpPolicyLookAheadWindow: Int? = null,
    val otpPolicyPeriod: Int? = null,
    val otpSupportedApplications: List<String>? = null,
    val users: MutableList<UserRepresentation> = mutableListOf(),
    val federatedUsers: List<UserRepresentation>? = null,
    val scopeMappings: MutableList<ScopeMappingRepresentation> = mutableListOf(),
    val clientScopeMappings: Map<String, List<ScopeMappingRepresentation>>? = null,
    val clients: List<ClientRepresentation>? = null,
    val clientScopes: List<ClientScopeRepresentation>? = null,
    val defaultDefaultClientScopes: List<String>? = null,
    val defaultOptionalClientScopes: List<String>? = null,
    val browserSecurityHeaders: Map<String, String>? = null,
    val smtpServer: Map<String, String>? = null,
    val userFederationProviders: List<UserFederationProviderRepresentation>? = null,
    val userFederationMappers: MutableList<UserFederationMapperRepresentation> = mutableListOf(),
    val loginTheme: String? = null,
    val accountTheme: String? = null,
    val adminTheme: String? = null,
    val emailTheme: String? = null,
    @JsonProperty("eventsEnabled")
    val isEventsEnabled: Boolean? = null,
    val eventsExpiration: Long? = null,
    val eventsListeners: List<String>? = null,
    val enabledEventTypes: List<String>? = null,
    @JsonProperty("adminEventsEnabled")
    val isAdminEventsEnabled: Boolean? = null,
    @JsonProperty("adminEventsDetailsEnabled")
    val isAdminEventsDetailsEnabled: Boolean? = null,
    val identityProviders: MutableList<IdentityProviderRepresentation> = mutableListOf(),
    val identityProviderMappers: MutableList<IdentityProviderMapperRepresentation> = mutableListOf(),
    val protocolMappers: MutableList<ProtocolMapperRepresentation> = mutableListOf(),
    val components: Map<String, ComponentExportRepresentation>? = null,
    @JsonProperty("internationalizationEnabled")
    val isInternationalizationEnabled: Boolean? = null,
    val supportedLocales: MutableSet<String> = mutableSetOf(),
    val defaultLocale: String? = null,
    val authenticationFlows: List<AuthenticationFlowRepresentation>? = null,
    val authenticatorConfig: List<AuthenticatorConfigRepresentation>? = null,
    val requiredActions: List<RequiredActionProviderRepresentation>? = null,
    val browserFlow: String? = null,
    val registrationFlow: String? = null,
    val directGrantFlow: String? = null,
    val resetCredentialsFlow: String? = null,
    val clientAuthenticationFlow: String? = null,
    val dockerAuthenticationFlow: String? = null,
    val attributes: Map<String, String> = mapOf(),
    val keycloakVersion: String? = null,
    @JsonProperty("userManagedAccessAllowed")
    val isUserManagedAccessAllowed: Boolean? = null
) {


    fun user(username: String?): UserRepresentation {
        val user = UserRepresentation()
        user.username = username
        users.add(user)
        return user
    }

    fun clientScopeMapping(clientName: String?): ScopeMappingRepresentation {
        val mapping = ScopeMappingRepresentation(client = clientName)
        scopeMappings.add(mapping)
        return mapping
    }

    fun clientScopeScopeMapping(clientScopeName: String?): ScopeMappingRepresentation {
        val mapping = ScopeMappingRepresentation(clientScope = clientScopeName)
        scopeMappings.add(mapping)
        return mapping
    }

    fun addUserFederationMapper(userFederationMapper: UserFederationMapperRepresentation) {
        userFederationMappers.add(userFederationMapper)
    }

    fun addIdentityProvider(identityProviderRepresentation: IdentityProviderRepresentation) {
        identityProviders.add(identityProviderRepresentation)
    }

    fun addProtocolMapper(rep: ProtocolMapperRepresentation) {
        protocolMappers.add(rep)
    }

    fun addSupportedLocales(locale: String) {
        supportedLocales.add(locale)
    }

    fun addIdentityProviderMapper(rep: IdentityProviderMapperRepresentation) {
        identityProviderMappers.add(rep)
    }

    val isIdentityFederationEnabled: Boolean
        get() = identityProviders.isNotEmpty()

}