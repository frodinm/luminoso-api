package com.luminoso.authorization.config

import com.luminoso.authorization.service.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.TokenRequest
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory
import org.springframework.security.oauth2.provider.token.TokenStore

@Suppress("SpringJavaAutowiredMembersInspection")
class Oauth2RequestFactory(clientDetailsService: ClientDetailsService) : DefaultOAuth2RequestFactory(clientDetailsService) {

    @Autowired
    private lateinit var tokenStore: TokenStore

    @Autowired
    private lateinit var userDetailsService: UserDetailsServiceImpl

    override fun createTokenRequest(requestParameters: MutableMap<String, String>, authenticatedClient: ClientDetails): TokenRequest {
        if (requestParameters["grant_type"].equals("refresh_token")) {
            val authentication = tokenStore.readAuthenticationForRefreshToken(
                tokenStore.readRefreshToken(requestParameters["refresh_token"]))
            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(authentication.name, null,
                userDetailsService.loadUserByUsername(authentication.name).authorities)
        }
        return super.createTokenRequest(requestParameters, authenticatedClient)
    }
}