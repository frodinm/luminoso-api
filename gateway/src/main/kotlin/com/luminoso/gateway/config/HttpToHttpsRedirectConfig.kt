package com.luminoso.gateway.config

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import reactor.core.publisher.Mono
import java.net.URI
import java.net.URISyntaxException
import javax.annotation.PostConstruct


@Configuration
class HttpToHttpsRedirectConfig(private val environment: Environment) {
//    @PostConstruct
//    fun startRedirectServer() {
//        val httpNettyReactiveWebServerFactory = NettyReactiveWebServerFactory(8081)
//        httpNettyReactiveWebServerFactory.getWebServer { request: ServerHttpRequest, response: ServerHttpResponse ->
//            val uri: URI = request.uri
//            val httpsUri: URI
//            try {
//                httpsUri = URI("https", uri.userInfo, uri.host, environment.getProperty("local.server.port")!!.toInt(), uri.path, uri.query, uri.fragment)
//            } catch (e: URISyntaxException) {
//                return@getWebServer Mono.error<Void>(e)
//            }
//            response.statusCode = HttpStatus.MOVED_PERMANENTLY
//            response.headers.location = httpsUri
//            response.setComplete()
//        }.start()
//    }
}
