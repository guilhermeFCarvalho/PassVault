package com.example.passvault.features.password.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.passvault.features.password.domain.model.Password

@Database(
    entities = [Password::class],
    version = 1
)

abstract class PasswordDatabase : RoomDatabase() {
    abstract val passwordDao: PasswordDao
}