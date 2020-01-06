package com.luminoso.websitemanagement.models.messaging

data class Tenant (val alias: String, val issuerUri: String = "", val jwkSetUri: String = "")
