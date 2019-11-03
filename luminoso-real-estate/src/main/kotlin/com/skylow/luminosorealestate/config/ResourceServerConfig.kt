package com.skylow.luminosorealestate.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.access.vote.RoleHierarchyVoter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler
import org.springframework.security.web.FilterInvocation
import org.springframework.security.access.expression.SecurityExpressionHandler

@Configuration
@EnableResourceServer
class ResourceServerConfig: ResourceServerConfigurerAdapter() {

    // TODO: Make this work ... Followed a few solutions. Ex : https://stackoverflow.com/questions/32566520/custom-rolehierarchy-not-working-with-method-security-in-spring-boot-web-applica
    //       But nothing worked.
    @Bean
    fun roleHierarchy(): RoleHierarchyImpl {
        val roleHierarchy = RoleHierarchyImpl()
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER")
        return roleHierarchy
    }

    @Bean
    fun roleHierarchyVoter(): RoleHierarchyVoter {
        return RoleHierarchyVoter(roleHierarchy())
    }


    private fun webExpressionHandler(): SecurityExpressionHandler<FilterInvocation> {
        val defaultWebSecurityExpressionHandler = DefaultWebSecurityExpressionHandler()
        defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy())
        return defaultWebSecurityExpressionHandler
    }

    override fun configure(http: HttpSecurity) {
       http.authorizeRequests()
           .expressionHandler(webExpressionHandler())
           .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
           .antMatchers("/spaces/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
           .antMatchers("/actuator/health").permitAll()
           .anyRequest()
           .authenticated()
    }
}