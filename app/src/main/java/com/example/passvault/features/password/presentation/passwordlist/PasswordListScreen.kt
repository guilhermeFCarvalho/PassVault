package com.example.passvault.features.password.presentation.passwordlist

import android.content.ClipData
import android.content.ClipDescription
import android.os.PersistableBundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.SortByAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.passvault.core.navigation.Screen
import com.example.passvault.features.password.presentation.passwordlist.component.PasswordItemComponent
import com.example.passvault.features.password.presentation.passwordlist.event.PasswordEvent
import com.example.passvault.features.password.presentation.passwordlist.state.PasswordState
import com.example.passvault.ui.theme.PassVaultTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordListScreen(
    state: PasswordState,
    onEvent: (PasswordEvent) -> Unit,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    val scope = rememberCoroutineScope()
    val snackbarState = remember { SnackbarHostState() }

    val clipboard = LocalClipboardManager.current

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(snackbarState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigate(Screen.AddPasswordsScreen.route)
                },
                content = {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add Password"
                    )
                },
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text("Passwords")
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(PasswordEvent.OrderPasswords)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.SortByAlpha,
                            contentDescription = "Sort"
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.passwords) { password ->
                PasswordItemComponent(
                    password = password,
                    deleteOnClick = {
                        onEvent(PasswordEvent.DeletePassword(password.id))
                        scope.launch {
                            val result = snackbarState.showSnackbar(
                                message = "Password deleted!",
                                actionLabel = "Undo"
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                onEvent(PasswordEvent.RestorePassword)
                            }
                        }
                    },
                    editOnClick = {
                        onNavigate(Screen.AddPasswordsScreen.route + "?passwordId=${password.id}")
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
            }
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    PassVaultTheme {
        PasswordListScreen(
            state = PasswordState(
                passwords = listOf(
                    PasswordState.Password(
                        password = "123",
                        label = "test",
                        id = 0
                    )
                )
            ),
            onEvent = {},
            onNavigate = {}
        )
    }
}