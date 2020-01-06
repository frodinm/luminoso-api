package com.luminoso.authorization.config

import com.luminoso.authorization.repository.cookies.impl.CookieRepository
import com.luminoso.authorization.usecases.cookie.IGetCookieUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuth2AuthenticationFailureHandler(private val getCookieUseCase: IGetCookieUseCase) : SimpleUrlAuthenticationFailureHandler() {

    @Autowired
    lateinit var cookieOAuth2AuthorizationRequestRepository: CookieRepository


    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        var targetUrl = "/"

        getCookieUseCase.getRedirectUri(request).map {
            if (it != null) targetUrl = it.value

            targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.localizedMessage)
                .build().toUriString()

            cookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response)

            redirectStrategy.sendRedirect(request, response, targetUrl)
        }.subscribe()
    }

}