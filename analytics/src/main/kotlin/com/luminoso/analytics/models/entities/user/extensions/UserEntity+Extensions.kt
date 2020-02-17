package com.luminoso.analytics.models.entities.user.extensions

import com.luminoso.analytics.models.entities.analytics.extensions.toDbEntity
import com.luminoso.analytics.models.entities.analytics.extensions.toExternal
import com.luminoso.analytics.models.entities.user.UserEntity
import com.luminoso.analytics.models.pojo.user.User

fun UserEntity.toExternal(): User {
    return User(analytics!!.toExternal())
}

fun User.toDbEntity(): UserEntity {
    val userEntity = UserEntity()
    userEntity.analytics = analytics.toDbEntity()
    return userEntity
}
