package com.luminoso.usersmanagement.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "luminoso")
class EnvProperties {
    val environment = Environment()
    val authorization = Authorization()
    val authentication = Authentication()

    class Environment {
        lateinit var gateway: String
    }

    class Authorization {
        lateinit var serverUrl: String
        lateinit var username: String
        lateinit var password: String
        lateinit var clientId: String
    }

    class Authentication {
        lateinit var realm: String
    }
}