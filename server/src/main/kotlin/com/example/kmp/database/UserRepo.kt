package com.example.kmp.database


import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.kmp.exceptions.BadRequestException
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


class UserRepo {

    fun getAll(): List<User> = transaction {
        UserTable.selectAll().map {
            User(
                id = it[UserTable.id].toLong(),
                username = it[UserTable.username]
            )
        }
    }

    fun createUser(username: String, password: String): User = transaction {
        val exists = UserTable
            .selectAll().where(UserTable.username eq username)
            .count() > 0

        if (exists)
            throw BadRequestException("Username already exists")

        val hashed = BCrypt.withDefaults()
            .hashToString(12, password.toCharArray())

        val id = UserTable.insert {
            it[UserTable.username] = username
            it[UserTable.password] = hashed
        } get UserTable.id

        User(id.toLong(), username)
    }

    fun getUserByUsername(username: String): User? = transaction {
        UserTable
            .selectAll()
            .where(UserTable.username eq username)
            .singleOrNull()
            ?.let {
                User(
                    id = it[UserTable.id].toLong(),
                    username = it[UserTable.username]
                )
            }
    }

    fun verifyPassword(user: User, rawPassword: String): Boolean = transaction {
        val row = UserTable.selectAll()
            .where(UserTable.id eq user.id.toInt())
            .single()

        val storedHash = row[UserTable.password]
        BCrypt.verifyer().verify(rawPassword.toCharArray(), storedHash).verified
    }

    fun authenticate(username: String, password: String): User? {
        val user = getUserByUsername(username) ?: return null
        return if (verifyPassword(user, password)) user else null
    }

    fun register(req: RegisterRequest): User {
        val id = transaction {
            val exists =
                UserTable
                    .selectAll()
                    .where(UserTable.username eq req.username)
                .count() > 0
            if (exists) throw BadRequestException("Username already exists")
            UserTable . insert {
            it[username] = req.username
            // store bcrypt hash
                val hashed = BCrypt
                    .withDefaults()
                    .hashToString(12, req.password.toCharArray())
                it[password] = hashed } get UserTable.id
        }
        return User(id.toLong(), req.username)
    }
        }