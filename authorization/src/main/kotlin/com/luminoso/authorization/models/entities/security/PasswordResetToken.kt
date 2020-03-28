package com.luminoso.authorization.models.entities.security

import com.luminoso.authorization.models.entities.security.user.UserEntity
import com.luminoso.commonjpa.entities.GeneratedIdBaseEntity
import java.util.*
import javax.persistence.*


@Entity
data class PasswordResetToken(
    var token: String,
    var expiryDate: Date) : GeneratedIdBaseEntity() {

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    var authUser: UserEntity? = null

    private fun calculateExpiryDate(expiryTimeInMinutes: Int): Date {
        val cal = Calendar.getInstance()
        cal.timeInMillis = Date().time
        cal.add(Calendar.MINUTE, expiryTimeInMinutes)
        return Date(cal.time.time)
    }

    fun updateToken(token: String) {
        this.token = token
        this.expiryDate = calculateExpiryDate(EXPIRATION)
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
