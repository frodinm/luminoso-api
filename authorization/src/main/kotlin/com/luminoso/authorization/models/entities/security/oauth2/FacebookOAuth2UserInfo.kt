package com.luminoso.authorization.models.entities.security.oauth2

class FacebookOAuth2UserInfo(private val attributes: Map<String, Any>) : OAuth2UserInfo {

    override fun getId(): String {
        return attributes["id"] as String
    }

    override fun firstName(): String {
        return attributes["first_name"] as String
    }

    override fun lastName(): String {
        return attributes["last_name"] as String
    }

    override fun getName(): String {
        return attributes["name"] as String
    }

    override fun getEmail(): String {
        return attributes["email"] as String
    }

    override fun getImageUrl(): String {
        if (attributes.containsKey("picture")) {
            val pictureObj = attributes["picture"] as Map<*, *>
            if (pictureObj.containsKey("data")) {
                val dataObj = pictureObj["data"] as Map<*, *>
                if (dataObj.containsKey("url")) {
                    return dataObj["url"] as String
                }
            }
        }

        return ""
    }
}