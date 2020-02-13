package com.luminoso.communications.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import javax.annotation.PostConstruct


@Configuration
class FirebaseConfig {
    @PostConstruct
    fun init() {
        val resource = ClassPathResource("config/luminoso-dev-firebase-adminsdk.json")
        val options = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(resource.inputStream))
            .build()
        FirebaseApp.initializeApp(options)
    }
}
