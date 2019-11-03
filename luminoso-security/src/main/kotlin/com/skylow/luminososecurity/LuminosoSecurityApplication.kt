package com.skylow.luminososecurity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.provisioning.JdbcUserDetailsManager

@SpringBootApplication
class LuminosoSecurityApplication {
//	@Bean
//	fun servletContainer(): ServletWebServerFactory {
//		val tomcat = object : TomcatServletWebServerFactory() {
//			override fun postProcessContext(context: Context) {
//				val securityConstraint = SecurityConstraint()
//				securityConstraint.userConstraint = "CONFIDENTIAL"
//				val collection = SecurityCollection()
//				collection.addPattern("/*")
//				securityConstraint.addCollection(collection)
//				context.addConstraint(securityConstraint)
//			}
//		}
//		tomcat.addAdditionalTomcatConnectors(redirectConnector())
//		return tomcat
//	}

//	private fun redirectConnector(): Connector {
//		val connector = Connector("org.apache.coyote.http11.Http11NioProtocol")
//		connector.scheme = "http"
//		connector.port = 8080
//		connector.secure = false
//		connector.redirectPort = 8443
//		return connector
//	}
}

fun main(args: Array<String>) {
	runApplication<LuminosoSecurityApplication>(*args)
}
