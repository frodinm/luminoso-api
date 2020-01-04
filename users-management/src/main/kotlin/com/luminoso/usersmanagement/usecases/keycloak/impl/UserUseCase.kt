package com.luminoso.usersmanagement.usecases.keycloak.impl

import com.luminoso.usersmanagement.models.pojo.authentication.SignUp
import com.luminoso.usersmanagement.models.pojo.representations.idm.CredentialRepresentation
import com.luminoso.usersmanagement.models.pojo.representations.idm.UserRepresentation
import com.luminoso.usersmanagement.usecases.keycloak.IUserUseCase
import org.springframework.stereotype.Component
import java.util.HashMap
import java.util.regex.Pattern

@Component
class UserUseCase: IUserUseCase {
    override fun toUser(alias: String, signUp: SignUp): UserRepresentation {
        val credentialRepresentation = CredentialRepresentation(type = "password", value = signUp.password)
        val attributes: MutableMap<String, List<String>> = HashMap()
        attributes["tenant_id"] = listOf(alias)

        val fullNameSplit = signUp.fullName.split(" ")
        val username = signUp.companyEmail.split(Pattern.compile("/(.*?)(?=@)/g"))[0]

        return UserRepresentation(
            username = username,
            credentials = listOf(credentialRepresentation),
            isEnabled = true,
            isEmailVerified = true,
            firstName = fullNameSplit[0],
            lastName = fullNameSplit[1],
            email = signUp.companyEmail,
            attributes = attributes
        )
    }
}