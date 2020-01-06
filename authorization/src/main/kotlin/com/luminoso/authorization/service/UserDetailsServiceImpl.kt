package com.luminoso.authorization.service

import com.luminoso.authorization.models.exceptions.EmailNotVerifiedException
import com.luminoso.authorization.repository.UserRepository
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("userDetailsService")
class UserDetailsServiceImpl(private val repository: UserRepository) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user = repository.findByUserName(username) ?: throw UsernameNotFoundException("Username or password wrong")
        if (!user.emailVerified) {
            throw EmailNotVerifiedException("UserEntity email not verified")
        }
        // Throws Exception if any of authorization values return false
        AccountStatusUserDetailsChecker().check(user)
        return user
    }
}