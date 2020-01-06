package com.luminoso.authorization.usecases.security.impl

import com.luminoso.authorization.enums.AuthProvider
import com.luminoso.authorization.models.entities.security.oauth2.GoogleOAuth2UserInfo
import com.luminoso.authorization.models.entities.security.oauth2.OAuth2UserInfo
import com.luminoso.authorization.models.exceptions.OAuth2AuthenticationProcessingException
import com.luminoso.authorization.usecases.security.IGetOauth2UserInfoUseCase
import org.springframework.stereotype.Component

@Component
class GetOauth2UserInfoUseCaseUseCase : IGetOauth2UserInfoUseCase {
    override fun get(registrationId: String, attributes: Map<String, Any>): OAuth2UserInfo {
        return when (registrationId.toLowerCase()) {
            AuthProvider.GOOGLE.name.toLowerCase() -> GoogleOAuth2UserInfo(attributes)
            else -> throw OAuth2AuthenticationProcessingException("Login with $registrationId is not supported yet.")
        }
    }
}