package com.luminoso.usersmanagement.usecases.keycloak.impl

import com.luminoso.usersmanagement.models.pojo.authentication.SignUp
import com.luminoso.usersmanagement.models.pojo.representations.idm.*
import com.luminoso.usersmanagement.usecases.keycloak.IKeycloakRealmUseCase
import com.luminoso.usersmanagement.usecases.keycloak.IUserUseCase
import org.springframework.stereotype.Component
import java.util.*

@Component
class KeycloakRealmUseCase(private val userUseCase: IUserUseCase): IKeycloakRealmUseCase {

    override fun hrTenantRealm(alias: String, signUp: SignUp): RealmRepresentation {
        val clientScopes = mutableListOf<ClientScopeRepresentation>()
        clientScopes.add(ClientScopeRepresentation(name = "message:read", protocol = "openid-connect"))

        val clients = listOf(
            toMessagesClient(alias),
            toCommunicationsClient(alias),
            toCandidateManagementClient(alias),
            toTcsClient(alias),
            toWebClient(alias)
        )

        val users = mutableListOf<UserRepresentation>()
        users.add(userUseCase.toUser(alias,signUp))

        return RealmRepresentation(
            realm = alias,
            isEnabled = true,
            clients = clients,
            clientScopes = clientScopes,
            users = users,
            ssoSessionMaxLifespan = 1209600, // 14 days
            ssoSessionIdleTimeout = 1209600,
            accessTokenLifespan = 604800 // 7 days
        )
    }

    override fun toMessagesClient(alias: String): ClientRepresentation {
        val attributes: MutableMap<String, Any> = HashMap()
        attributes["use.jwks.url"] = true
        attributes["jwks.url"] = "http://$alias:8080/jwks"

        return ClientRepresentation(
            clientId = "message-service",
            clientAuthenticatorType = "client-secret",
            isEnabled = true,
            isBearerOnly = false,
            redirectUris = listOf("http://$alias:8080/login/oauth2/code/$alias", "http://$alias:8080"),
            isStandardFlowEnabled = true,
            isDirectAccessGrantsEnabled = true,
            isPublicClient = false,
            protocolMappers = listOf(protocolMapper("tenant_id"), protocolMapper("user_id")),
            defaultClientScopes = listOf("role_list", "profile", "email"),
            optionalClientScopes = listOf("address", "message:read", "phone", "offline_access", "admin"),
            attributes = attributes.toMap()
        )
    }

    override fun toCommunicationsClient(alias: String): ClientRepresentation {
        return ClientRepresentation(
            clientId = "communications-service",
            clientAuthenticatorType = "client-secret",
            isEnabled = true,
            isBearerOnly = false,
            redirectUris = listOf("http://$alias:8080"),
            isStandardFlowEnabled = false,
            isDirectAccessGrantsEnabled = false,
            isServiceAccountsEnabled = true,
            isPublicClient = false,
            protocolMappers = listOf(protocolMapper("tenant_id"), protocolMapper("user_id")),
            defaultClientScopes = listOf("role_list", "profile", "email"),
            optionalClientScopes = listOf("address", "phone", "offline_access", "admin")
        )
    }

    override fun toCandidateManagementClient(alias: String): ClientRepresentation {
        return ClientRepresentation(
            clientId = "candidate-management-service",
            clientAuthenticatorType = "client-secret",
            isEnabled = true,
            isBearerOnly = false,
            redirectUris = listOf("http://$alias:8080"),
            isStandardFlowEnabled = false,
            isDirectAccessGrantsEnabled = false,
            isServiceAccountsEnabled = true,
            isPublicClient = false,
            protocolMappers = listOf(protocolMapper("tenant_id"), protocolMapper("user_id")),
            defaultClientScopes = listOf("role_list", "profile", "email"),
            optionalClientScopes = listOf("address", "phone", "offline_access", "admin")
        )
    }

    override fun toWebClient(alias: String): ClientRepresentation {
        return ClientRepresentation(
            clientId = "web-client",
            clientAuthenticatorType = "client-secret",
            isEnabled = true,
            isBearerOnly = false,
            redirectUris = listOf("http://$alias:8080"),
            isStandardFlowEnabled = false,
            isImplicitFlowEnabled = true,
            isDirectAccessGrantsEnabled = true,
            isServiceAccountsEnabled = false,
            isPublicClient = true,
            protocolMappers = listOf(protocolMapper("tenant_id"), protocolMapper("user_id")),
            defaultClientScopes = listOf("role_list", "profile", "email"),
            optionalClientScopes = listOf("address", "phone", "offline_access", "admin")
        )
    }

    override fun toTcsClient(alias: String): ClientRepresentation {
        return ClientRepresentation(
            clientId = "tenant-context-service",
            clientAuthenticatorType = "client-secret",
            isEnabled = true,
            isBearerOnly = false,
            redirectUris = listOf("http://$alias:8080"),
            isStandardFlowEnabled = true,
            isDirectAccessGrantsEnabled = false,
            isServiceAccountsEnabled = true,
            isPublicClient = false,
            protocolMappers = listOf(protocolMapper("tenant_id"), protocolMapper("user_id")),
            defaultClientScopes = listOf("role_list", "profile", "email"),
            optionalClientScopes = listOf("address", "phone", "offline_access", "admin")
        )
    }

    override fun tenantRealm(alias: String): RealmRepresentation {
        return RealmRepresentation(
            realm = alias,
            isEnabled = true
        )
    }

    private fun protocolMapper(claimName: String): ProtocolMapperRepresentation {
        val tenantIdConfig: MutableMap<String, Any> = HashMap()
        tenantIdConfig["userinfo.token.claim"] = true
        tenantIdConfig["user.attribute"] = claimName
        tenantIdConfig["id.token.claim"] = true
        tenantIdConfig["access.token.claim"] = true
        tenantIdConfig["claim.name"] = claimName
        tenantIdConfig["jsonType.label"] = "String"

        return ProtocolMapperRepresentation(
            name = claimName,
            protocol = "openid-connect",
            protocolMapper = "oidc-usermodel-attribute-mapper",
            isConsentRequired = false,
            config = tenantIdConfig.toMap()
        )
    }

}