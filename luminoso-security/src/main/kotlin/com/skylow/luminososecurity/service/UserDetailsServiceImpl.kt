//package com.skylow.luminososecurity.service
//
//import com.skylow.luminososecurity.dao.UserDao
//import com.skylow.luminososecurity.exceptions.EmailNotVerifiedException
//import org.springframework.security.authentication.AccountStatusUserDetailsChecker
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.core.userdetails.UsernameNotFoundException
//import org.springframework.stereotype.Service
//import org.springframework.transaction.annotation.Transactional
//
//@Service("UserDetailsService")
//class UserDetailsServiceImpl(private val userDao: UserDao) : UserDetailsService {
//
//    @Transactional
//    override fun loadUserByUsername(username: String): UserDetails {
//        val user = userDao.findByUserName(username) ?: throw UsernameNotFoundException("Username or password wrong")
//        if (!user.emailVerified) {
//            throw EmailNotVerifiedException("UserEntity email not verified")
//        }
//        // Throws Exception if any of authorization values return false
//        AccountStatusUserDetailsChecker().check(user)
//        return user
//    }
//}