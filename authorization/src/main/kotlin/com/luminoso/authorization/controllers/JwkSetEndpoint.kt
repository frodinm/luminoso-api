package com.luminoso.authorization.controllers

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.security.KeyPair
import java.security.Principal
import java.security.interfaces.RSAPublicKey


@FrameworkEndpoint
internal class JwkSetEndpoint(var keyPair: KeyPair) {
    @GetMapping("/.well-known/jwks.json")
    @ResponseBody
    fun getKey(principal: Principal?): Map<String, Any> {
        val publicKey: RSAPublicKey = keyPair.public as RSAPublicKey
        val key: RSAKey = RSAKey.Builder(publicKey).build()
        return JWKSet(key).toJSONObject()
    }

}
