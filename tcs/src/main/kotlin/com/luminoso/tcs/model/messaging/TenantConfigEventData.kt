package com.luminoso.tcs.model.messaging

data class TenantConfigEventData(val action: TenantConfigAction, val hostName: String, val issuer: String? = null)