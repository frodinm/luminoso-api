package com.luminoso.analytics.config

import nl.basjes.parse.useragent.UserAgentAnalyzer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserAnalyserConfig {
    @Bean
    fun userAgentAnalyser(): UserAgentAnalyzer {
        return UserAgentAnalyzer
            .newBuilder()
            .hideMatcherLoadStats()
            .withCache(10000)
            .build()
    }
}
