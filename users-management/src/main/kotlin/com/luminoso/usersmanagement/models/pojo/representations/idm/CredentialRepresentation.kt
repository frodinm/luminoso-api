package com.luminoso.usersmanagement.models.pojo.representations.idm

import com.fasterxml.jackson.annotation.JsonProperty


data class CredentialRepresentation(
    val type: String? = null,
    val device: String? = null,
    // Plain-text value of credential (used for example during import from manually created JSON file)
    val value: String? = null,
    // Value stored in DB (used for example during export/import)
    val hashedSaltedValue: String? = null,
    val salt: String? = null,
    val hashIterations: Int? = null,
    val counter: Int? = null,
    val algorithm: String? = null,
    val digits: Int? = null,
    val period: Int? = null,
    val createdDate: Long? = null,
    val config: Map<String, String>? = null,
    // only used when updating a credential.  Might set required action
    @JsonProperty("temporary")
    val isTemporary: Boolean? = null
) {

    companion object {
        const val SECRET = "secret"
        const val PASSWORD = "password"
        const val PASSWORD_TOKEN = "password-token"
        const val TOTP = "totp"
        const val HOTP = "hotp"
        const val CLIENT_CERT = "cert"
        const val KERBEROS = "kerberos"
    }
}