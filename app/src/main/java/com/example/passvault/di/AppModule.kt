package com.example.passvault.di
import androidx.room.Room
import com.example.passvault.features.password.data.database.PasswordDatabase
import com.example.passvault.features.password.data.repository.PasswordRepositoryImpl
import com.example.passvault.features.password.domain.repository.PasswordRepository
import com.example.passvault.features.password.domain.usecase.AddPasswordUseCase
import com.example.passvault.features.password.domain.usecase.DeletePasswordUseCase
import com.example.passvault.features.password.domain.usecase.GetPasswordsUseCase
import com.example.passvault.features.password.domain.usecase.GetSinglePasswordUseCase
import com.example.passvault.features.password.domain.usecase.PasswordUseCases
import com.example.passvault.features.password.presentation.addpassword.AddPasswordViewModel
import com.example.passvault.features.password.presentation.passwordlist.PasswordViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {
    single{
        Room.databaseBuilder(
            context = androidContext(),
            klass = PasswordDatabase::class.java,
            name = PasswordDatabase.DATABASE_NAME ).build()
    }

    single{
        get<PasswordDatabase>().passwordDao
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
    viewModel <PasswordViewModel>{
        PasswordViewModel(get())
    }
    viewModelOf(::AddPasswordViewModel)


}