package com.example.passvault.features.password.presentation.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.passvault.features.password.domain.model.Password


@Composable
fun HidePasswordButtonComponent(
    isPasswordVisible: Boolean,
    onClick: (isPasswordVisible: Boolean) -> Unit
) {
    Icon(
        if (isPasswordVisible) {
            Icons.Filled.Visibility
        } else {
            Icons.Filled.VisibilityOff
        },
        contentDescription = "Toggle password visibility",
        modifier = Modifier
            .size(16.dp)
            .padding(horizontal = 4.dp)
            .clickable { onClick(!isPasswordVisible) }
    )
}