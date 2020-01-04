package com.luminoso.messages.tenant

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import org.springframework.dao.DataAccessException
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory

class TenantMongoDbFactory(mongoClient: MongoClient, databaseName: String) : SimpleMongoClientDbFactory(mongoClient, databaseName) {
    @Throws(DataAccessException::class)
    override fun getDb(dbName: String): MongoDatabase {
        val tenant = TenantResolver.resolve()
        return super.getDb(tenant + "_" + dbName)
    }
}