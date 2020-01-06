package com.luminoso.authorization.usecases.security

import com.luminoso.authorization.models.entities.security.oauth2.OAuth2UserInfo

interface IGetOauth2UserInfoUseCase {
    fun get(registrationId: String, attributes: Map<String, Any>): OAuth2UserInfo
}