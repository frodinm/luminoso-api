package com.luminoso.authorization.models.entities.security

import com.luminoso.authorization.models.entities.security.user.UserEntity
import com.luminoso.commonjpa.entities.NonGeneratedIdBaseEntity
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "verification_token")
data class VerificationToken(
    var token: String
) : NonGeneratedIdBaseEntity() {

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    var authUser: UserEntity? = null

    var expiryDate = calculateExpiryDate()

    private fun calculateExpiryDate(): Date {
        val cal = Calendar.getInstance()
        cal.timeInMillis = Date().time
        cal.add(Calendar.MINUTE, EXPIRATION)
        return Date(cal.time.time)
    }


    fun updateToken(token: String) {
        this.token = token
        this.expiryDate = calculateExpiryDate()
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Token [String=").append(token).append("]").append("[Expires").append(expiryDate).append("]")
        return builder.toString()
    }

    companion object {
        private const val EXPIRATION = 60 * 24
    }
}
