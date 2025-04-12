package com.example.passvault.features.password.presentation.addpassword.di

import com.example.passvault.features.password.presentation.addpassword.AddPasswordViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val addPasswordModule = module {
    viewModelOf(::AddPasswordViewModel)
}