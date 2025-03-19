package com.example.passvault.core.presentation.util

sealed class Screens (val route: String){
    object PasswordsScreen: Screens(route = "/passwords_screen")
    object AddPasswordsScreen: Screens(route = "/add_passwords_screen")

}