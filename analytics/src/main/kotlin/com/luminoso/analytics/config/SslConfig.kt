package com.luminoso.analytics.config

import org.apache.http.client.HttpClient
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.conn.ssl.TrustSelfSignedStrategy
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContextBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.web.client.RestTemplate
import javax.net.ssl.SSLContext

@Configuration
class SslConfig(private val oAuth2ResourceServerProperties: OAuth2ResourceServerProperties) {
    @Value("\${server.ssl.key-store}")
    private val keyStore: Resource? = null
    @Value("\${server.ssl.key-store-password}")
    private val keyStorePassword: String? = null

    @Bean
    fun restTemplate(): RestTemplate {
        val sslContext: SSLContext = SSLContextBuilder().loadTrustMaterial(null, TrustSelfSignedStrategy()
            ).build()
        val socketFactory = SSLConnectionSocketFactory(sslContext,NoopHostnameVerifier.INSTANCE)
        val httpClient: HttpClient = HttpClients.custom()
            .setSSLSocketFactory(socketFactory)
            .build()

        val factory = HttpComponentsClientHttpRequestFactory(httpClient)
        return RestTemplate(factory)
    }

    @Bean
    fun jwtDecoder(): NimbusJwtDecoder {
        return NimbusJwtDecoder
            .withJwkSetUri(oAuth2ResourceServerProperties.jwt.jwkSetUri)
            .restOperations(restTemplate())
            .build()
    }

}
