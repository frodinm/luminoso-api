package com.luminoso.authorization.config

import com.luminoso.authorization.models.exceptions.BadRequestException
import com.luminoso.authorization.usecases.cookie.IAuthenticationCookieUseCase
import com.luminoso.authorization.usecases.cookie.IGetCookieUseCase
import com.luminoso.authorization.usecases.cookie.IRemoveCookieUseCase
import com.luminoso.authorization.usecases.security.ITokenProviderUseCase
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import java.net.URI
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuth2AuthenticationSuccessHandler(private val getCookieUseCase: IGetCookieUseCase,
                                         private val removeCookieUseCase: IRemoveCookieUseCase,
                                         private val authenticationCookieUseCase: IAuthenticationCookieUseCase) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        // Block required in order to avoid response being already committed
        val targetUrl = determineTargetUrl(request).block()
        if (response.isCommitted) {
            logger.debug("Response has already been committed. Unable to redirect to $targetUrl")
        } else {
//            val token = tokenProviderUseCase.createZucciniWebToken(authentication).block()
            clearAuthenticationAttributes(request, response)
//            authenticationCookieUseCase.add(DefaultOAuth2AccessToken(token), response)
            redirectStrategy.sendRedirect(request, response, targetUrl)
        }
    }

    private fun determineTargetUrl(request: HttpServletRequest): Mono<String> {
        return getCookieUseCase.getRedirectUri(request).map {
            val redirectUri = it?.value
            if (!redirectUri.isNullOrEmpty() && !isAuthorizedRedirectUri(redirectUri)) {
                throw BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication")
            }

            var targetUrl = defaultTargetUrl

            if (!redirectUri.isNullOrEmpty()) {
                targetUrl = redirectUri
            }

            UriComponentsBuilder.fromUriString(targetUrl)
                .build().toUriString()
        }
    }

    private fun clearAuthenticationAttributes(request: HttpServletRequest, response: HttpServletResponse) {
        super.clearAuthenticationAttributes(request)
        removeCookieUseCase.removeAuthorization(request, response)
    }

    private fun isAuthorizedRedirectUri(uri: String): Boolean {
        val clientRedirectUri = URI.create(uri)
//        luminosoProperties.oauth2.web.authorizedRedirectUris.map {
//            val authorizedRedirectUri = URI.create(it)
//            if (authorizedRedirectUri.host == clientRedirectUri.host && authorizedRedirectUri.port == clientRedirectUri.port) {
//                return true
//            }
//        }
        return true
    }
}