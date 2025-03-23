package com.example.passvault.features.password.presentation.password_list.screens

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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.SortByAlpha
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.passvault.core.presentation.util.Screens
import com.example.passvault.features.password.presentation.password_list.PasswordEvent
import com.example.passvault.features.password.presentation.password_list.PasswordViewModel
import com.example.passvault.features.password.presentation.password_list.components.PasswordItemComponent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun PasswordListScreen(
    navController: NavController,
    viewModel : PasswordViewModel = koinViewModel()
) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val clipboard = LocalClipboardManager.current

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.AddPasswordsScreen.route)
                },
                content = {
                    Icon(Icons.Default.Add, contentDescription = "Add Password")
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
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
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Password deleted!",
                                    actionLabel = "Undo",

                                    )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(PasswordEvent.RestorePassword)
                                }
                            }
                        },
                        editOnClick = {
                            navController.navigate(Screens.AddPasswordsScreen.route + "?passwordId=${password.id}")
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


