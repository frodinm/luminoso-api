package com.luminoso.usersmanagement.models.messaging

data class TenantConfigEventData(val action: TenantConfigAction, val hostName: String, val issuer: String? = null)