package com.luminoso.authorization.usecases.user.impl

import com.luminoso.authorization.models.entities.security.user.UserEntity
import com.luminoso.authorization.repository.UserRepository
import com.luminoso.authorization.usecases.user.IGetUserUseCase
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Component
class GetUserUseCase(private val repository: UserRepository) : IGetUserUseCase {
    override fun byId(userId: Int): Mono<UserEntity?> {
        return Mono.fromCallable {
            val user = repository.findById(userId)
            if (user.isPresent) {
                user.get()
            } else {
                null
            }
        }.subscribeOn(Schedulers.elastic())
    }

    override fun byEmail(email: String): Mono<UserEntity?> {
        return Mono.fromCallable {
            repository.findByEmail(email)
        }.subscribeOn(Schedulers.elastic())
    }
}