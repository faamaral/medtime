package com.digitaldose.medtime.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.digitaldose.medtime.ui.theme.CustomColors

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 24/02/2025
 */

@Composable
fun CustomOutlinedTextField(value: String, onValueChange: (String) -> Unit, label: String){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                label,
                fontWeight = FontWeight.SemiBold,
                color = CustomColors.OutlinedTextFieldLabelColor,
                fontSize = 16.sp
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = CustomColors.OutlinedTextFiledContainerColor,
            focusedContainerColor = CustomColors.OutlinedTextFiledContainerColor,
            focusedBorderColor = CustomColors.OutlinedTextFieldBorderColor,
            unfocusedBorderColor = CustomColors.OutlinedTextFieldBorderColor,
            focusedLabelColor = CustomColors.OutlinedTextFieldLabelColor
        ),
        shape = RoundedCornerShape(8.dp)
    )
}