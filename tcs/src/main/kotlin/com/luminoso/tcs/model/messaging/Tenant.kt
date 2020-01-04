package com.luminoso.tcs.model.messaging

data class Tenant (val alias: String, val issuerUri: String = "", val jwkSetUri: String = "")
