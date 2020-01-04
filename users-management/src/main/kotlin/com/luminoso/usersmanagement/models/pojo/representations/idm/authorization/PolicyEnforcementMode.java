package com.luminoso.usersmanagement.models.pojo.representations.idm.authorization;

public enum PolicyEnforcementMode {

    /**
     * Requests are denied by default even when there is no policy associated with a given resource.
     */
    ENFORCING,

    /**
     * Requests are allowed even when there is no policy associated with a given resource.
     */
    PERMISSIVE,

    /**
     * Completely disables the evaluation of policies and allow access to any resource.
     */
    DISABLED
}
