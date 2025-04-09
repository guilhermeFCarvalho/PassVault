package com.example.passvault.features.password.presentation.passwordlist.di

import com.example.passvault.features.password.presentation.passwordlist.PasswordListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val passwordListModule = module {
    viewModelOf(::PasswordListViewModel)
}