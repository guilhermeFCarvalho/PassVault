package com.example.passvault

import android.app.Application
import com.example.passvault.features.password.data.database.di.databaseModule
import com.example.passvault.features.password.data.repository.di.repositoryModule
import com.example.passvault.features.password.domain.di.domainModule
import com.example.passvault.features.password.presentation.addpassword.di.addPasswordModule
import com.example.passvault.features.password.presentation.passwordlist.di.passwordListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)

            modules(
                domainModule,
                databaseModule,
                repositoryModule,
                passwordListModule,
                addPasswordModule,
            )
        }
    }
}