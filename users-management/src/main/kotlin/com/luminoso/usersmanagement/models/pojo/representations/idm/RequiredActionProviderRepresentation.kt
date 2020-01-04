package com.luminoso.usersmanagement.models.pojo.representations.idm

data class RequiredActionProviderRepresentation(
    val alias: String? = null,
    /**
     * Used for display purposes.  Probably should clean this code up and make alias and name the same, but
     * the old code references an Enum and the admin console creates a "friendly" name for each enum.
     *
     * @return
     */
    val name: String? = null,
    val providerId: String? = null,
    val isEnabled: Boolean = false,
    val isDefaultAction: Boolean = false,
    val priority: Int = 0,
    val  config: Map<String, String> = HashMap()
)