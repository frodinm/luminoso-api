package com.luminoso.messages.config

import com.luminoso.messages.model.Message
import com.luminoso.messages.repository.MessageRepository
import org.springframework.beans.factory.SmartInitializingSingleton
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.*

@Component
internal class MessageInitializer(private val messages: MessageRepository) : SmartInitializingSingleton {
    override fun afterSingletonsInstantiated() {
        val robId = UUID.fromString("6fb5a754-c666-4859-9452-f885796ee73e")
        val joeId = UUID.fromString("94d835cc-c70f-47c1-8206-2ad7c8a37565")
        val joshId = UUID.fromString("219168d2-1da4-4f8a-85d8-95b4377af3c1")
        val filipId = UUID.fromString("a2b2c791-e05a-4934-be9b-fb488f87700a")
        val riaId = UUID.fromString("3df7633b-0375-4609-a256-93bab5d19762")
        val one = "soullabs"
        val two = "client1"
        val three = "client2"

        val withTenantOne = withTenant(one)
        val tenantOneMessages = listOf(
            messages.save(Message(to = robId, from = joeId, text = "Hello World", tenant = one)),
            messages.save(Message(to = robId, from = joeId, text = "Greetings Spring Enthusiasts", tenant = one)),
            messages.save(Message(to = joeId, from = robId, text = "Hola", tenant = one)),
            messages.save(Message(to = joeId, from = robId, text = "Hey Java Devs", tenant = one)),
            messages.save(Message(to = robId, from = joshId, text = "Aloha", tenant = one)),
            messages.save(Message(to = joshId, from = robId, text = "Welcome to Spring", tenant = one))
        )

        val withTenantTwo = withTenant(two)
        val tenantTwoMessages = listOf(
            messages.save(Message(to = joshId, from = filipId, text = "SAML is the bomb", tenant = two)),
            messages.save(Message(to = filipId, from = joshId, text = "no doubt", tenant = two))
        )

        val withTenantThree = withTenant(three)
        val tenantThreeMessages = listOf(
            messages.save(Message(to = joshId, from = riaId, text = "Hey, nice job adding multi-tenancy, man!", tenant = three))
        )

        withTenantOne.and(Mono.`when`(tenantOneMessages)).subscribe()
        withTenantTwo.and(Mono.`when`(tenantTwoMessages)).subscribe()
        withTenantThree.and(Mono.`when`(tenantThreeMessages)).subscribe()

    }

    private fun withTenant(tenant: String): Mono<Unit> {
       return Mono.fromCallable {
            val principal: OAuth2AuthenticatedPrincipal = DefaultOAuth2AuthenticatedPrincipal(Collections.singletonMap<String, Any>("tenant_id", tenant), emptyList())
            val token = OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "token", Instant.now(), Instant.now().plusSeconds(1))
            val authentication = BearerTokenAuthentication(principal, token, emptyList())
            val context = SecurityContextHolder.getContext()
            context.authentication = authentication
        }.ignoreElement()
    }

}