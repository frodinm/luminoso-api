package com.luminoso.authorization.config

import com.luminoso.authorization.repository.cookies.impl.CookieOAuth2AuthorizationRequestRepository
import com.luminoso.authorization.service.OAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
class WebSecurityConfig(private val userOAuth2Service: OAuth2UserService,
                        private val cookieOAuth2AuthorizationRequestRepository: CookieOAuth2AuthorizationRequestRepository,
                        private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
                        private val oAuth2AuthenticationFailureHandler: OAuth2AuthenticationFailureHandler): WebSecurityConfigurerAdapter() {

    @Bean
    fun getAuthenticationManager(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
            .csrf().disable()
            .authorizeRequests { configurer ->
                configurer
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/register").permitAll()
                    .antMatchers("/actuator/health").permitAll()
                    .antMatchers("/oauth/**").permitAll()
                    .antMatchers("/error/**").permitAll()
                    .antMatchers("/.well-known/jwks.json").permitAll()
                    .anyRequest().authenticated()
            }.exceptionHandling {
                it.authenticationEntryPoint { _, response, authenticationException ->
                    if(authenticationException != null) {
                        response.status = HttpServletResponse.SC_UNAUTHORIZED;
                    }
                }
            }.oauth2Login { oauth2Login ->
                oauth2Login.authorizationEndpoint {
                    it.baseUri("/oauth2/authorize")
                    it.authorizationRequestRepository(HttpSessionOAuth2AuthorizationRequestRepository())
                }.userInfoEndpoint {
                    it.userService(userOAuth2Service)
                }

                oauth2Login.successHandler(oAuth2AuthenticationSuccessHandler)
                oauth2Login.failureHandler(oAuth2AuthenticationFailureHandler)
            }

    }
}
