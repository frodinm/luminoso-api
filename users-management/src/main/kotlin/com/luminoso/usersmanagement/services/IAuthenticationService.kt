package com.luminoso.usersmanagement.services

import com.luminoso.usersmanagement.models.messaging.Tenant
import com.luminoso.usersmanagement.models.pojo.authentication.SignUp
import reactor.core.publisher.Mono


interface IAuthenticationService {
    fun registerTenant(tenant: Tenant, signUp: SignUp): Mono<Tenant>
    fun login()
//    fun sendVerifyUserEmail(username: String): Mono<Unit>
}