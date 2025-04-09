package com.example.passvault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.passvault.core.navigation.Screen
import com.example.passvault.features.password.presentation.addpassword.AddPasswordScreen
import com.example.passvault.features.password.presentation.passwordlist.PasswordListScreen
import com.example.passvault.ui.theme.PassVaultTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PassVaultTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.PasswordsScreen.route
                    ) {
                        composable(route = Screen.PasswordsScreen.route) {
                            PasswordListScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddPasswordsScreen.route + "?passwordId={passwordId}",
                            arguments = listOf(
                                navArgument(name = "passwordId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            AddPasswordScreen(navController = navController)

                        }
                    }
                }
            }
        }
    }
}
