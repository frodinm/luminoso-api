package com.luminoso.usersmanagement

import com.luminoso.usersmanagement.models.messaging.TenantCreatedChannel
import com.luminoso.usersmanagement.properties.EnvProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.context.annotation.Bean
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository
import org.springframework.web.reactive.function.client.*

@EnableBinding(TenantCreatedChannel::class)
@EnableConfigurationProperties(EnvProperties::class)
@SpringBootApplication
class UsersManagementApplication {

    @Bean
    fun rest(clients: ReactiveClientRegistrationRepository,
             authz: ServerOAuth2AuthorizedClientRepository): WebClient {
        val oauth2 = ServerOAuth2AuthorizedClientExchangeFilterFunction(clients, authz)
        oauth2.setDefaultClientRegistrationId("keycloak")

        return WebClient.builder().filter(oauth2).build()
    }

}
fun main(args: Array<String>) {
    runApplication<UsersManagementApplication>(*args)
}
