package com.digitaldose.medtime.ui.components

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.digitaldose.medtime.R
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 15/11/2024
 */

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerComponent(addHorario: (String) -> Unit) {
    //TODO add date picker state

    //TODO add time picker state
    val timePickerState = rememberTimePickerState(
        initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        initialMinute = Calendar.getInstance().get(Calendar.MINUTE),
        is24Hour = true
    )
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedHorario by remember { mutableStateOf("") }

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

//        Text(text = "No Date Selected", modifier = Modifier.padding(bottom = 16.dp))

//        Button(
//            onClick = {
//                //TODO show date picker
//            },
//            modifier = Modifier.fillMaxWidth(),
//        ) {
//            Text(text = "Date Picker")
//        }

        Text(text = "Horário Inicial")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedHorario,
                onValueChange = {},
                placeholder = {
                    Text(text = "00:00")
                },
//                    label = {
//                        Text(text = "Horário Inicial")
//                    },
                modifier = Modifier.clickable(onClick = {
                    showTimePicker = true
                })

            )
            IconButton(
                onClick = {
                    showTimePicker = true
                },
//                    modifier = Modifier.fillMaxHeight()
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_access_alarm_24),
                    contentDescription = "Select Time",
                    tint = Color.DarkGray
                )
            }


        }
//        Text(text = selectedHorario?: "No Time Selected" , modifier = Modifier.padding(bottom = 16.dp))
//
//        Button(
//            onClick = {
//                showTimePicker = true
//            },
//            modifier = Modifier.fillMaxWidth(),
//        ) {
//            Text(text = "Time Picker")
//        }

    }

    //TODO show date picker when state is true


    //TODO show time picker when state is true
    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedTime = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                            set(Calendar.MINUTE, timePickerState.minute)
                        }
//                        selectedHorario = timePickerState.hour.toString() + ":" + timePickerState.minute.toString()
                        when {
                            timePickerState.hour <= 9 && timePickerState.minute <= 9 -> {
                                selectedHorario = "0${selectedTime.get(Calendar.HOUR_OF_DAY)}:0${selectedTime.get(Calendar.MINUTE)}"
                            }
                            timePickerState.hour <= 9 && timePickerState.minute > 9 -> {
                                selectedHorario = "0${selectedTime.get(Calendar.HOUR_OF_DAY)}:${selectedTime.get(Calendar.MINUTE)}"
                            }
                            timePickerState.hour > 9 && timePickerState.minute <= 9 -> {
                                selectedHorario = "${selectedTime.get(Calendar.HOUR_OF_DAY)}:${selectedTime.get(Calendar.MINUTE)}"
                            }
                            else -> {
                                selectedHorario =
                                    "${selectedTime.get(Calendar.HOUR_OF_DAY)}:${selectedTime.get(Calendar.MINUTE)}"
                            }

                        }

//                        var formatter = DateTimeFormatter.ofPattern("HH:mm")
//                        val horario = LocalTime.parse("${selectedTime.get(Calendar.HOUR_OF_DAY)}:${selectedTime.get(Calendar.MINUTE)}", formatter)
//                        selectedHorario = horario.format(formatter)
                        addHorario(
                            selectedHorario
//                            "${selectedTime.get(Calendar.HOUR_OF_DAY)}:${
//                                selectedTime.get(
//                                    Calendar.MINUTE
//                                )
//                            }"
                        )
                        showTimePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                    }
                ) { Text("Cancel") }
            }
        )
        {
            TimePicker(state = timePickerState)
        }
    }

}

@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onDismissRequest: () -> Unit,
    confirmButton: @Composable (() -> Unit),
    dismissButton: @Composable (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = containerColor
                ),
            color = containerColor
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}