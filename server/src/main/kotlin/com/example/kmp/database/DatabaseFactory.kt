package com.example.kmp.database

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.Database

object DatabaseFactory {

    fun init() {
        Database.connect(
            url = "jdbc:h2:file:./data.db",
            driver = "org.h2.Driver",
            user = "root",
            password = ""
        )

        transaction {
            SchemaUtils.create(UserTable)
        }
    }
}
