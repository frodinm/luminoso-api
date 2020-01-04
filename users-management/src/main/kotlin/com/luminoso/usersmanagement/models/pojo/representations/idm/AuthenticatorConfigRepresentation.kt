package com.luminoso.usersmanagement.models.pojo.representations.idm

import java.io.Serializable
import java.util.*

class AuthenticatorConfigRepresentation(
    val id: String? = null,
    val alias: String? = null,
    val config: Map<String, String> = HashMap()
): Serializable