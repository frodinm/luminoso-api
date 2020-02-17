package com.luminoso.authorization.usecases.user.impl

import com.luminoso.authorization.enums.AuthProvider
import com.luminoso.authorization.models.exceptions.UserAlreadyExistException
import com.luminoso.authorization.models.pojo.User
import com.luminoso.authorization.repository.RoleRepository
import com.luminoso.authorization.repository.UserRepository
import com.luminoso.authorization.models.entities.security.user.UserAuthProvider
import com.luminoso.authorization.models.entities.security.user.UserEntity
import com.luminoso.authorization.models.entities.security.VerificationToken
import com.luminoso.authorization.models.auth.body.RegisterUserBody
import com.luminoso.authorization.models.entities.security.user.extensions.toUser
import com.luminoso.authorization.usecases.messaging.impl.SendCreatedUserEvent
import com.luminoso.authorization.usecases.user.ICreateUserUseCase
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
class CreateUserUseCase(private val userDao: UserRepository,
                        private val roleDao: RoleRepository,
                        private val passwordEncoder: PasswordEncoder,
                        private val sendCreatedUserEvent: SendCreatedUserEvent) : ICreateUserUseCase {

    override fun email(user: RegisterUserBody): Mono<UserEntity> {
        return Mono.fromCallable {
            if (emailExists(user.email)) {
                throw UserAlreadyExistException("Email is already registered")
            }
            if (userNameExists(user.username)) {
                throw UserAlreadyExistException("Username is taken")
            }

            val roles = linkedSetOf(roleDao.findByName("ROLE_USER"))

            val userEntity = UserEntity(
                username = user.username,
                password = passwordEncoder.encode(user.password),
                email = user.email
            )

            userEntity.roles.addAll(roles)

            val token = UUID.randomUUID().toString()
            userEntity.verificationToken = VerificationToken(token)
            userEntity.addAuthProvider(UserAuthProvider(provider = AuthProvider.EMAIL))

            userDao.save(userEntity)
        }.doOnSuccess {
            sendCreatedUserEvent.send(it.toUser())
        }.subscribeOn(Schedulers.elastic())
    }

    override fun sso(user: User): Mono<UserEntity> {
        return Mono.fromCallable {
            val initialUserName = user.email.substring(0, user.email.indexOf("@"))

            if (emailExists(user.email)) {
                throw UserAlreadyExistException("Email is already registered")
            }
            if (userNameExists(initialUserName)) {
                throw UserAlreadyExistException("Username is taken")
            }

            val roles = linkedSetOf(roleDao.findByName("ROLE_USER"))

            val userEntity = UserEntity(
                username = initialUserName,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email
            )

            userEntity.roles.addAll(roles)

            userEntity.addAuthProvider(UserAuthProvider(provider = AuthProvider.GOOGLE))

            userDao.save(userEntity)
        }.subscribeOn(Schedulers.elastic())
    }

    private fun emailExists(email: String): Boolean {
        return userDao.findByEmail(email) != null
    }

    private fun userNameExists(username: String): Boolean {
        return userDao.findByUserName(username) != null
    }
}
