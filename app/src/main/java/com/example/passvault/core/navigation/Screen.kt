package com.example.passvault.core.navigation

sealed class Screen(val route: String) {
    data object PasswordsScreen : Screen(route = "passwords_screen")
    data object AddPasswordsScreen : Screen(route = "add_passwords_screen")
}