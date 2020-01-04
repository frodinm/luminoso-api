package com.luminoso.usersmanagement.usecases.keycloak

import com.luminoso.usersmanagement.models.pojo.authentication.SignUp
import com.luminoso.usersmanagement.models.pojo.representations.idm.ClientRepresentation
import com.luminoso.usersmanagement.models.pojo.representations.idm.RealmRepresentation


interface IKeycloakRealmUseCase {
    fun toMessagesClient(alias: String): ClientRepresentation
    fun toCommunicationsClient(alias: String): ClientRepresentation
    fun toCandidateManagementClient(alias: String): ClientRepresentation
    fun toWebClient(alias: String): ClientRepresentation
    fun toTcsClient(alias: String): ClientRepresentation
    fun tenantRealm(alias: String): RealmRepresentation
    fun hrTenantRealm(alias: String, signUp: SignUp): RealmRepresentation
}