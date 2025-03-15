package com.example.passvault.features.password.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.passvault.features.password.domain.model.Password
import kotlinx.coroutines.flow.Flow


@Dao
interface PasswordDao {
    @Query("SELECT * FROM password")
    fun getPasswords(): Flow<List<Password>>

    @Query("SELECT * FROM password WHERE id = :id")
    suspend fun getPasswordById(id: Int): Password?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassword(password: Password)

    @Delete
    suspend fun deletePassword(password: Password)
}