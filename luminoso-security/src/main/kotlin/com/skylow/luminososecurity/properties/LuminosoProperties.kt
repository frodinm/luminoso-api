package com.skylow.luminososecurity.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "luminoso")
class LuminosoProperties {
    val auth = Auth()
    val oauth2 = OAuth2()
    val mail = Mail()

    class OAuth2 {
        val web = Web()
        val adminWeb = AdminWeb()

        class Web {
            lateinit var clientId: String
            lateinit var clientSecret: String
            lateinit var scope: List<String>
            lateinit var authorizedRedirectUris: List<String>
        }

        class AdminWeb {
            lateinit var clientId: String
            lateinit var clientSecret: String
            lateinit var scope: List<String>
            lateinit var authorizedRedirectUris: List<String>
        }
    }

    class Auth {
        lateinit var keypass: String
        var tokenExpirationMsec: Long = 864000000
    }

    class Mail {
        lateinit var supportEmail: String
        var isSendingEmail: Boolean = true
    }
}