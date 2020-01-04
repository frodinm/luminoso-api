package com.luminoso.usersmanagement.models.pojo.representations.idm.authorization

open class AbstractPolicyRepresentation {
    var id: String? = null
    var name: String? = null
    var description: String? = null
    var type: String? = null
    var policies = mutableSetOf<String>()
    private var resources = mutableSetOf<String>()
    var scopes =  mutableSetOf<String>()
    var logic = Logic.POSITIVE
    var decisionStrategy = DecisionStrategy.UNANIMOUS

    fun addPolicy(vararg id: String) {
        policies.addAll(arrayListOf(*id))

    }

    fun addResource(id: String) {
        resources.add(id)
    }

    fun addScope(vararg id: String) {
        scopes.addAll(listOf(*id))
    }
}