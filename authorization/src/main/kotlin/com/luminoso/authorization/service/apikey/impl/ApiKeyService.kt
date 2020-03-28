package com.luminoso.authorization.service.apikey.impl

import com.luminoso.authorization.service.apikey.IApiKeyService
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.TokenRequest
import org.springframework.stereotype.Service

@Service
class ApiKeyService: IApiKeyService {
    override fun createApiKey(authentication: OAuth2Authentication): OAuth2AccessToken {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getApiKeyAccessToken(authentication: OAuth2Authentication): OAuth2AccessToken {
        TODO("Not yet implemented")
    }


}
