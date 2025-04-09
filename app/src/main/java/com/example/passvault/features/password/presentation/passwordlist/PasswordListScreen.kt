package com.example.passvault.features.password.presentation.passwordlist

import android.content.ClipData
import android.content.ClipDescription
import android.os.PersistableBundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.SortByAlpha
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.passvault.core.navigation.Screen
import com.example.passvault.features.password.presentation.passwordlist.component.PasswordItemComponent
import com.example.passvault.features.password.presentation.passwordlist.event.PasswordEvent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun PasswordListScreen(
    navController: NavController,
    viewModel: PasswordViewModel = koinViewModel()
) {

    val state = viewModel.state.value

    val scope = rememberCoroutineScope()
    val snackbarState = remember { SnackbarHostState() }

    val clipboard = LocalClipboardManager.current

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddPasswordsScreen.route)
                },
                content = {
                    Icon(Icons.Default.Add, contentDescription = "Add Password")
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text("Passwords")
                IconButton(onClick = { viewModel.onEvent(PasswordEvent.OrderPasswords) }) {
                    Icon(
                        imageVector = Icons.Outlined.SortByAlpha,
                        contentDescription = "Sort"
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.passwords) { password ->
                    PasswordItemComponent(
                        password = password,
                        deleteOnClick = {
                            viewModel.onEvent(PasswordEvent.DeletePassword(password))
                            scope.launch {
                                val result = snackbarState.showSnackbar(
                                    message = "Password deleted!",
                                    actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(PasswordEvent.RestorePassword)
                                }
                            }
                        },
                        editOnClick = {
                            navController.navigate(Screen.AddPasswordsScreen.route + "?passwordId=${password.id}")
                        },
                        copyOnClick = {
                            val clipData = ClipData.newPlainText("Password", password.password)
                            clipData.apply {
                                description.extras = PersistableBundle().apply {
                                    putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, true)
                                }
                            }
                            val clipEntry = ClipEntry(clipData)
                            clipboard.setClip(clipEntry)
                        }
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}
