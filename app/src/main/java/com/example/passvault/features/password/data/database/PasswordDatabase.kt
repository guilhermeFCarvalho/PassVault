package com.example.passvault.features.password.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.passvault.features.password.data.database.model.PasswordEntity
import com.example.passvault.features.password.domain.model.Password

@Database(
    entities = [PasswordEntity::class],
    version = 1
)
abstract class PasswordDatabase : RoomDatabase() {
    abstract val passwordDao: PasswordDao

    companion object {
        const val DATABASE_NAME = "passwords_db"
    }
}