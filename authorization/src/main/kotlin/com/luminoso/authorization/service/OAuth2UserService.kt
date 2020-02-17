package com.luminoso.authorization.service

import com.luminoso.authorization.enums.AuthProvider
import com.luminoso.authorization.models.entities.security.oauth2.OAuth2UserInfo
import com.luminoso.authorization.models.entities.security.user.UserAuthProvider
import com.luminoso.authorization.models.entities.security.user.UserEntity
import com.luminoso.authorization.models.exceptions.OAuth2AuthenticationProcessingException
import com.luminoso.authorization.models.pojo.User
import com.luminoso.authorization.usecases.security.IGetOauth2UserInfoUseCase
import com.luminoso.authorization.usecases.user.ICreateUserUseCase
import com.luminoso.authorization.usecases.user.IGetUserUseCase
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class OAuth2UserService(private val getOauth2UserInfoUseCase: IGetOauth2UserInfoUseCase,
                        private val createUserUseCase: ICreateUserUseCase,
                        private val getUserUseCase: IGetUserUseCase): DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        return try {
            processOauth2User(userRequest, super.loadUser(userRequest))
        } catch (ex: AuthenticationException) {
            throw InternalAuthenticationServiceException(ex.message, ex.cause)
        }
    }

    private fun processOauth2User(oAuth2UserRequest: OAuth2UserRequest, oauth2User: OAuth2User): OAuth2User {
        val oauth2UserInfo = getOauth2UserInfoUseCase.get(oAuth2UserRequest.clientRegistration.registrationId, oauth2User.attributes)
        if (oauth2UserInfo.getEmail().isEmpty()) {
            throw OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider")
        }

        return getUserUseCase.byEmail(oauth2UserInfo.getEmail()).switchIfEmpty(createUser(oauth2UserInfo)).flatMap { userEntity ->
            if(userEntity!!.userAuthProviders.contains(UserAuthProvider(AuthProvider.valueOf(oAuth2UserRequest.clientRegistration.registrationId.toUpperCase()))).not()) {
                createUser(oauth2UserInfo)
            } else {
                Mono.just(oauth2User)
            }
        }.block()!!
    }

    private fun createUser(oauth2UserInfo: OAuth2UserInfo): Mono<UserEntity> {
        val user = User(
            username = "${oauth2UserInfo.firstName()}_${oauth2UserInfo.lastName()}",
            firstName = oauth2UserInfo.firstName(),
            lastName = oauth2UserInfo.lastName(),
            email = oauth2UserInfo.getEmail(),
            password = "",
            picture = oauth2UserInfo.getImageUrl())
        return createUserUseCase.sso(user)
    }
}
