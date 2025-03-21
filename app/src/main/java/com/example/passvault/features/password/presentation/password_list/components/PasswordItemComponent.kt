package com.example.passvault.features.password.presentation.password_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.ModeEditOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passvault.features.password.domain.model.Password
import com.example.passvault.features.password.presentation.shared.components.HidePasswordButtonComponent
import com.example.passvault.ui.theme.PassVaultTheme


@Composable
fun PasswordItemComponent(
    password: Password,
    deleteOnClick: () -> Unit,
    editOnClick: () -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    PassVaultTheme {
        Card() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column{
                    Row {
                        Text(text = password.label)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AnimatedVisibility(visible = isPasswordVisible) {
                            Text(
                                text = password.password,
                                style = MaterialTheme.typography.body2.copy(fontSize = 10.sp)
                            )
                        }
                        AnimatedVisibility(visible = !isPasswordVisible) {
                            Text(
                                text = "•".repeat(password.password.length),
                                style = MaterialTheme.typography.body1,
                            )
                        }

                        HidePasswordButtonComponent(
                            isPasswordVisible = isPasswordVisible,
                            onClick = { isPasswordVisible = it },
                        )
                    }
                }
                Spacer(Modifier.width(4.dp))
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Icon(
                        Icons.Filled.DeleteOutline,
                        contentDescription = "Delete Password",
                        modifier = Modifier
                            .size(16.dp)
                            .padding(end = 4.dp)
                            .clickable { deleteOnClick() }
                    )
                    Spacer(Modifier.height(12.dp))
                    Icon(
                        Icons.Filled.ModeEditOutline,
                        contentDescription = "Edit Password",
                        modifier = Modifier
                            .size(16.dp)
                            .padding(end = 4.dp)
                            .clickable { editOnClick() }
                    )
                    Spacer(Modifier.height(12.dp))
                    Icon(
                        Icons.Filled.CopyAll,
                        contentDescription = "Copy Password",
                        modifier = Modifier
                            .size(16.dp)
                            .padding(end = 4.dp)
                            .clickable {

                            }
                    )
                }

            }
        }
    }

}


@Preview
@Composable
fun PasswordItemComponentPreview() {
    PasswordItemComponent(
        password = Password(
            label = "Netflix",
            password = "AbcAbc123.213",

            ),
        editOnClick = {},
        deleteOnClick = {},
    )
}