package com.skylow.luminososecurity.models.security.oauth2

class GoogleOAuth2UserInfo(private val attributes: Map<String, Any>) : OAuth2UserInfo {
    override fun getId(): String {
        return attributes["sub"] as String
    }

    override fun getName(): String {
        return attributes["name"] as String
    }

    override fun firstName(): String {
        return attributes["given_name"] as String
    }

    override fun lastName(): String {
        return attributes["family_name"] as String
    }

    override fun getEmail(): String {
        return attributes["email"] as String
    }

    override fun getImageUrl(): String {
        return attributes["picture"] as String
    }
}