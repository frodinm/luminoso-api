package com.skylow.luminososecurity.models.security.user

import com.skylow.luminososecurity.models.entities.GeneratedIdBaseEntity
import com.skylow.luminososecurity.models.security.PasswordResetToken
import com.skylow.luminososecurity.models.security.Role
import com.skylow.luminososecurity.models.security.VerificationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

import javax.persistence.*

@Entity
@Table(name = "users")
data class AuthUserEntity(
    var firstName: String = "",
    var lastName: String = "",
    @JvmField var username: String = "",
    var email: String = "",
    @JvmField var password: String = "",
    var emailVerified: Boolean = false,
    var accountNonExpired: Boolean = true,
    var accountNonLocked: Boolean = true,
    var credentialsNonExpired: Boolean = true,
    var enabled: Boolean = true) : GeneratedIdBaseEntity(), UserDetails {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")], inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
    val roles: MutableSet<Role> = mutableSetOf()

//    @OneToMany(mappedBy = "authUser", cascade = [CascadeType.ALL])
//    val userAuthProviders: MutableList<UserAuthProvider> = mutableListOf()

    @OneToOne(mappedBy = "authUser", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var verificationToken: VerificationToken? = null
        set(value) {
            field = value
            verificationToken?.authUser = this
        }

    @OneToOne(mappedBy = "authUser", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var passwordResetToken: PasswordResetToken? = null
        set(value) {
            field = value
            passwordResetToken?.authUser = this
        }

//    fun addAuthProvider(authProvider: UserAuthProvider) {
//        userAuthProviders.add(authProvider)
//        authProvider.authUser = this
//    }

//    fun removeAuthProvider(authProvider: UserAuthProvider) {
//        userAuthProviders.remove(authProvider)
//        authProvider.authUser = null
//    }

//    override fun getName(): String {
//        return username
//    }
//
//    override fun getAttributes(): MutableMap<String, Any> {
//        return mutableMapOf()
//    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val result = mutableListOf<GrantedAuthority>()

        roles.map {
            result.add(SimpleGrantedAuthority(it.name))
            it.permissions.map { permission ->
                result.add(SimpleGrantedAuthority(permission.name))
            }
        }

        return result
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    override fun getUsername(): String {
        return username
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired
    }

    override fun getPassword(): String {
        return password
    }

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked
    }
}
