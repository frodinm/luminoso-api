package com.skylow.luminososecurity.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.access.vote.RoleHierarchyVoter
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import javax.sql.DataSource

@EnableWebSecurity
class WebSecurityConfiguration {

    @Configuration
    @Order(1)
    inner class AuthenticationConfigurationAdapter(private val dataSource: DataSource) : WebSecurityConfigurerAdapter() {

        @Bean
        fun getAuthenticationManager(): AuthenticationManager {
            return super.authenticationManagerBean()
        }

        @Bean
        fun passwordEncoder(): PasswordEncoder {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder()
        }

        @Bean
        override fun userDetailsService(): UserDetailsService {
            return JdbcUserDetailsManager(dataSource)
        }

        @Bean
        fun roleHierarchy(): RoleHierarchyVoter {
            val roleHierarchy = RoleHierarchyImpl()
            roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER")
            return RoleHierarchyVoter(roleHierarchy)
        }

        override fun configure(http: HttpSecurity) {
            http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
        }
    }
}