package com.example.passvault.di

import androidx.room.Room
import com.example.passvault.features.password.data.datasource.PasswordDatabase
import com.example.passvault.features.password.data.repository.PasswordRepositoryImpl
import com.example.passvault.features.password.domain.repository.PasswordRepository
import com.example.passvault.features.password.domain.use_case.AddPasswordUseCase
import com.example.passvault.features.password.domain.use_case.DeletePasswordUseCase
import com.example.passvault.features.password.domain.use_case.GetPasswordsUseCase
import com.example.passvault.features.password.domain.use_case.GetSinglePasswordUseCase
import com.example.passvault.features.password.domain.use_case.PasswordUseCases
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single{
        Room.databaseBuilder(
            context = androidContext(),
            klass = PasswordDatabase::class.java,
            name = PasswordDatabase.DATABASE_NAME ).build()
    }
    single <PasswordRepository>{
        PasswordRepositoryImpl(get())
    }
    single<PasswordUseCases>{
        PasswordUseCases(
            getPasswordsUseCases = GetPasswordsUseCase(get()),
            deletePasswordUseCase = DeletePasswordUseCase(get()),
            addPasswordUseCase = AddPasswordUseCase(get()),
            getSinglePasswordUseCase = GetSinglePasswordUseCase(get()),
        )
    }


}