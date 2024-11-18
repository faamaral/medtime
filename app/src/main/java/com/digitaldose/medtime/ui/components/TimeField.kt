package com.digitaldose.medtime.ui.components

import android.app.TimePickerDialog
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.sp
import com.digitaldose.medtime.R

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 16/11/2024
 */

@Composable
fun TimeField(value: String, onValueChange: (String) -> Unit, modifier: Modifier, timePiker: TimePickerDialog) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        readOnly = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters,
        ),
        placeholder = {
            Text(
                text = "HH:mm",
                fontWeight = FontWeight(400),
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        },
        trailingIcon = {
            IconButton(onClick = {timePiker.show()}) {
                Icon(painter = painterResource(id = R.drawable.baseline_access_time_24), contentDescription = "Hora", tint = Color.DarkGray)
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer

        )
    )

}