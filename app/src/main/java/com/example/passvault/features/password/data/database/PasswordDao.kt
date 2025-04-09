package com.example.passvault.features.password.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.passvault.features.password.data.database.model.PasswordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Query("SELECT * FROM password")
    fun getPasswords(): Flow<List<PasswordEntity>>

    @Query("SELECT * FROM password WHERE id = :id")
    suspend fun getPasswordById(id: Int): PasswordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassword(password: PasswordEntity)

    @Delete
    suspend fun deletePassword(password: PasswordEntity)
}