package com.luminoso.analytics.models.entities.user

import com.luminoso.analytics.models.entities.GeneratedIdBaseEntity
import com.luminoso.analytics.models.entities.analytics.AnalyticsEntity
import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity: GeneratedIdBaseEntity() {

    @OneToOne(mappedBy = "analyticsUser", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var analytics: AnalyticsEntity? = null
        set(value) {
            field = value
            analytics?.analyticsUser = this
        }

}
