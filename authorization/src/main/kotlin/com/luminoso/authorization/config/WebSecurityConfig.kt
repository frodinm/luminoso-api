package com.luminoso.authorization.config

import com.luminoso.authorization.repository.cookies.impl.CookieOAuth2AuthorizationRequestRepository
import com.luminoso.authorization.service.OAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

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
        http.authorizeRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/actuator/health").permitAll()
            .antMatchers("/oauth/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2Login()
            .authorizationEndpoint()
            .baseUri("/oauth2/authorize")
            .authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository)
            .and()
            .userInfoEndpoint()
            .userService(userOAuth2Service)
            .and()
            .successHandler(oAuth2AuthenticationSuccessHandler)
            .failureHandler(oAuth2AuthenticationFailureHandler)
    }
}
