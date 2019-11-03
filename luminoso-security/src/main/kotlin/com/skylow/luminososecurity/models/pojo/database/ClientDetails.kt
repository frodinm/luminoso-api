package com.skylow.luminososecurity.models.pojo.database

data class ClientDetails(val webServerRedirectUri: String, val resourceIds: String, val scope: String) {
    fun getScopes(): Set<String> {
        return scope.split(",").map { it.trim() }.toSet()
    }
}