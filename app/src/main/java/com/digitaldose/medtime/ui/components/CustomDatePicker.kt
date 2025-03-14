package com.digitaldose.medtime.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.digitaldose.medtime.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 13/03/2025
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    value: String,
    addData: (String) -> Unit,
    label: String,
    modifier: Modifier

) {
    val datePickerState = rememberDatePickerState(

    )
    var selectedDate by remember { mutableStateOf<Long?>(if (value.isBlank()) null else stringParaLong(value)) }
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomOutlinedTextField(
                readOnly = true,
                value = selectedDate?.let { convertMillisToDate(it) } ?: "",
                onValueChange = {},
                label = label,
                placeholder = { Text("DD/MM/YYYY") },
//                modifier = Modifier.clickable(onClick = {
//                    showTimePicker = true
//                }),
                modifier = modifier.fillMaxWidth().pointerInput(selectedDate) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (upEvent != null) {
                            showDatePicker = true
                        }
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            showDatePicker = true
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_access_alarm_24),
                            contentDescription = "Select Time",
                            tint = Color.DarkGray
                        )
                    }
                }
            )


        }
    }
    if (showDatePicker) {
//        DatePickerDialog(
//            onDismissRequest = {},
//            confirmButton = {
//                TextButton(onClick = {
//                    datePickerState.selectedDateMillis
//                    addData(
//                        selectedDate?.let { convertMillisToDate(it) } ?: ""
//                    )
//                    showDatePicker = false
//                }) {
//                    Text("OK")
//                }
//            },
//            dismissButton = {
//                TextButton(
//                    onClick = {
//                        showDatePicker = false
//                    }
//                ) { Text("Cancel") }
//            }
//        ) {
//
//            DatePicker(state = datePickerState)
//
//        }
        DatePickerModal(
            addData,
            onDateSelected = { selectedDate = it },
            onDismiss = { showDatePicker = false }
        )
    }
//    if (showDatePicker) {
//        Popup(
//            onDismissRequest = { showDatePicker = false },
//            alignment = Alignment.TopStart
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .offset(y = 64.dp)
//                    .shadow(elevation = 4.dp)
//                    .background(MaterialTheme.colorScheme.surface)
//                    .padding(16.dp)
//            ) {
//                DatePicker(
//                    state = datePickerState,
//                    showModeToggle = false
//                )
//            }
//        }
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    addData: (String) -> Unit,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                addData(
                    datePickerState.selectedDateMillis?.let { convertMillisToDate(it) } ?: ""
                )
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
    return formatter.format(Date(millis))
}

fun stringParaLong(dataString: String, formato: String = "dd/MM/yyyy"): Long {
    val sdf = SimpleDateFormat(formato, Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
    return sdf.parse(dataString)?.time ?: 0L
}