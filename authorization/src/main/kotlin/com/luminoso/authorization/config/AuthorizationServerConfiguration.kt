package com.luminoso.authorization.config

import com.luminoso.authorization.service.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.OAuth2RequestFactory
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory
import java.security.KeyPair
import javax.sql.DataSource


@EnableAuthorizationServer
@Configuration
class AuthorizationServerConfiguration(
    private val passwordEncoder: PasswordEncoder,
    private val dataSource: DataSource,
    private val authenticationManager: AuthenticationManager,
    private val userDetailsServiceImpl: UserDetailsServiceImpl,
    private val clientDetailsService: ClientDetailsService) : AuthorizationServerConfigurer {

    @Bean
    fun keypair(): KeyPair {
        val ksFile = ClassPathResource("/keystore/keystore.p12")
        val ksFactory = KeyStoreKeyFactory(ksFile as Resource, "password".toCharArray())
        return ksFactory.getKeyPair("jwt")
    }

    @Bean
    fun jwtAccessTokenConverter(): JwtAccessTokenConverter {
        val converter = EnhancedJwtTokenConverter()
        converter.setKeyPair(keypair())
        return converter
    }


    @Bean
    fun tokenStore(): JwtTokenStore {
        return JwtTokenStore(jwtAccessTokenConverter())
    }

    @Bean
    fun requestFactory(): OAuth2RequestFactory {
        val requestFactory = Oauth2RequestFactory(clientDetailsService)
        requestFactory.setCheckUserScopes(true)
        return requestFactory
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security
            .checkTokenAccess("isAuthenticated()")
            .tokenKeyAccess("permitAll()")
//            .sslOnly()
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(tokenStore())
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsServiceImpl)
            .tokenEnhancer(jwtAccessTokenConverter())
    }

}
