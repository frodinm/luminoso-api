package com.luminoso.authorization.models.auth.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("uuid")
    val uuid: String
)
