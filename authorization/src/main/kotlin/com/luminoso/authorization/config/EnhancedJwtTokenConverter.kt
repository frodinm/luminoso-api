package com.luminoso.authorization.config

import com.luminoso.authorization.models.pojo.User
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import java.util.*

class EnhancedJwtTokenConverter : JwtAccessTokenConverter() {

    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {

        val info = LinkedHashMap(accessToken.additionalInformation)

        if(authentication.principal is User){
            val user: User = authentication.principal as User

            info["client_id"] = authentication.oAuth2Request.clientId
            info["sub"] = user
            info["user_name"] = user.username
            info["email"] = user.email
        }

        val customAccessToken = DefaultOAuth2AccessToken(accessToken)

        customAccessToken.additionalInformation = info

        return super.enhance(customAccessToken, authentication)
    }

}