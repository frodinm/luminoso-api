package com.luminoso.authorization.models.entities.security.oauth2

interface OAuth2UserInfo {
    fun getId(): String

    fun getName(): String

    fun firstName(): String

    fun lastName(): String

    fun getEmail(): String

    fun getImageUrl(): String
}