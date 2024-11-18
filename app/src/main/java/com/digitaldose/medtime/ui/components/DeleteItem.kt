package com.digitaldose.medtime.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 16/11/2024
 */

@Composable
fun DeleteItem(
    onConfirmButton: () -> Unit = {},
    onDismissButton: () -> Unit = {},
    setShowDialog: (Boolean) -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = {
            setShowDialog(false)
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirmButton()
                setShowDialog(false)
            }) {
                Text(text = "Sim")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissButton()
                    setShowDialog(false)
                }
            ) {
                Text(text = "Não")
            }
        },
        icon = {
            Icon(Icons.Filled.Delete, contentDescription = null)
        },
        title = {
            Text(text = "Deletar medicamento", fontSize = 20.sp)
        },
        text = {
            Text(text = "Deseja deletar este medicamento?")
        },
        modifier = Modifier.fillMaxWidth()
    )
}