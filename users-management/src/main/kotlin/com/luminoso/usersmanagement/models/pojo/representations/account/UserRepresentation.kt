package com.luminoso.usersmanagement.models.pojo.representations.account

class UserRepresentation {
    var id: String? = null
    var username: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var isEmailVerified = false
    var attributes: Map<String, List<String>>? = null

}