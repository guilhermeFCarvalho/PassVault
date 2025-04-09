package com.example.passvault.features.password.data.database.di

import androidx.room.Room
import com.example.passvault.features.password.data.database.PasswordDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = PasswordDatabase::class.java,
            name = PasswordDatabase.DATABASE_NAME
        ).build()
    }

    single {
        get<PasswordDatabase>().passwordDao
    }
}