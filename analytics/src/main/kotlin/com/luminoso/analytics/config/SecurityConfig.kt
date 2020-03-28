package com.luminoso.analytics.config


import com.luminoso.apiauthorization.core.security.extensions.oauth2ApiKeyResourceServer
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.jwt.JwtDecoder

@Configuration
@EnableWebSecurity(debug = true)
class SecurityConfig(
    private val jwtDecoder: JwtDecoder
   ): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/actuator/health").permitAll()
            .antMatchers("/event").permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer { configurer ->
                configurer.jwt().decoder(jwtDecoder)
            }.oauth2ApiKeyResourceServer(applicationContext)
    }
}
