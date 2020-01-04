package com.luminoso.usersmanagement.models.pojo.authentication

data class SignUp(val fullName: String, val companyName: String, val companyEmail: String, val companyWebsite: String, val numberOfEmployees: Int, val password: String)