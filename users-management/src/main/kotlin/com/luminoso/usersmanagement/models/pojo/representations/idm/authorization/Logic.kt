package com.luminoso.usersmanagement.models.pojo.representations.idm.authorization

/**
 * The decision strategy dictates how the policies associated with a given policy are evaluated and how a final decision
 * is obtained.
 *
*/
enum class Logic {
    /**
     * Defines that this policy follows a positive logic. In other words, the final decision is the policy outcome.
     */
    POSITIVE,
    /**
     * Defines that this policy uses a logical negation. In other words, the final decision would be a negative of the policy outcome.
     */
    NEGATIVE
}