package com.luminoso.authorization

import com.luminoso.authorization.messaging.channels.UserCreatedChannel
import org.apache.catalina.Context
import org.apache.catalina.connector.Connector
import org.apache.tomcat.util.descriptor.web.SecurityCollection
import org.apache.tomcat.util.descriptor.web.SecurityConstraint
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ServletWebServerFactory
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

@EnableBinding(UserCreatedChannel::class)
@SpringBootApplication
class AuthorizationApplication {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun servletContainer(): ServletWebServerFactory {
        val tomcat = object : TomcatServletWebServerFactory() {
            override fun postProcessContext(context: Context) {
                val securityConstraint = SecurityConstraint()
                securityConstraint.userConstraint = "CONFIDENTIAL"
                val collection = SecurityCollection()
                collection.addPattern("/*")
                securityConstraint.addCollection(collection)
                context.addConstraint(securityConstraint)
            }
        }
        tomcat.addAdditionalTomcatConnectors(redirectConnector())
        return tomcat
    }

    private fun redirectConnector(): Connector {
        val connector = Connector("org.apache.coyote.http11.Http11NioProtocol")
        connector.scheme = "http"
        connector.port = 8080
        connector.secure = false
        connector.redirectPort = 8443
        return connector
    }
}

fun main(args: Array<String>) {
    runApplication<AuthorizationApplication>(*args)
}
