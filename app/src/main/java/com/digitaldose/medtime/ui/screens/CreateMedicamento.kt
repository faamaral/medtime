package com.digitaldose.medtime.ui.screens

import android.annotation.SuppressLint
import android.icu.text.DateFormat
import android.icu.util.Calendar
import android.os.Build
import android.widget.NumberPicker
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.digitaldose.medtime.R
import com.digitaldose.medtime.models.Medicamento
import com.digitaldose.medtime.ui.components.DropdownMenuComponent
import com.digitaldose.medtime.ui.components.TimePickerComponent
import com.digitaldose.medtime.utils.constants.Routes
import com.digitaldose.medtime.viewmodels.MedicamentoState
import com.digitaldose.medtime.viewmodels.MedicamentoViewModel
import com.google.type.DateTime
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 15/11/2024
 */


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMedicamento(
    navController: NavController, modifier: Modifier, medicamentoViewModel: MedicamentoViewModel
) {
    var medicamentoState = medicamentoViewModel.medicamentoState.observeAsState()
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var dosagem by remember { mutableStateOf("") }
    var frequencia by remember { mutableStateOf("") }
    var horarios by remember { mutableStateOf(mutableListOf<String>()) }
    var horario by remember { mutableStateOf("") }
    var selectedTime: TimePickerState? by remember { mutableStateOf(null) }
    var count by remember { mutableStateOf(0) }

    val context = LocalContext.current

    val options = listOf("Comprimido(s)", "Pilula(s)", "Gota(s)")
    val frequenciaOptions = listOf("Diariamente", "Quando necessario", "Outro")
    var expanded by remember { mutableStateOf(false) }
    var expandedFrequencia by remember { mutableStateOf(false) }
    // Unidade de medida de dosagem
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    var selectedFrequenciaOptionText by remember { mutableStateOf(frequenciaOptions[0]) }

    LaunchedEffect(medicamentoState.value) {
        when (medicamentoState.value) {
            is MedicamentoState.Done -> {
                Toast.makeText(context, "Medicamento salvo com sucesso!", Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.HOME)
            }

            is MedicamentoState.Error -> {
                Toast.makeText(
                    context,
                    (medicamentoState.value as MedicamentoState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Novo medicamento")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Routes.HOME)
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding(), start = 8.dp, end = 8.dp)
                .fillMaxSize()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = nome,
                label = { Text(text = "Nome") },
                onValueChange = {
                    nome = it
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                value = descricao,
                label = { Text(text = "Descrição") },
                onValueChange = {
                    descricao = it
                },
                maxLines = 5,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {
                OutlinedTextField(
                    value = dosagem,
                    label = { Text(text = "Dosagem") },
                    onValueChange = {
                        dosagem = it
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.widthIn(250.dp, 450.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                DropdownMenuComponent(
                    options = options,
                    selectedOption = selectedOptionText,
                    onOptionSelected = { selectedOptionText = it },
                    label = "Unidade",
                    modifier = Modifier.fillMaxWidth()
                )
//                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
//                    expanded = !expanded
//                }) {
//                    TextField(
//                        readOnly = true,
//                        value = selectedOptionText,
//                        label = {
//                            Text(text = "Medida")
//                        }, onValueChange = {},
//                        trailingIcon = {
//                            ExposedDropdownMenuDefaults.TrailingIcon(
//                                expanded = expanded
//                            )
//                        },
//                        modifier = modifier.menuAnchor()
//                    )
//                    ExposedDropdownMenu(
//                        expanded = expanded,
//                        onDismissRequest = {
//                            expanded = false
//                        }
//                    ) {
//                        options.forEach { selectionOption ->
//                            DropdownMenuItem(onClick = {
//                                selectedOptionText = selectionOption
//                                expanded = false
//                            },
//                                text = {
//                                    Text(text = selectionOption)
//                                })
//                        }
//                    }
//                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            DropdownMenuComponent(
                options = frequenciaOptions,
                selectedOption = selectedFrequenciaOptionText,
                onOptionSelected = { selectedFrequenciaOptionText = it
                                   frequencia = selectedFrequenciaOptionText},
                label = "Frequência",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        count--
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    shape = CircleShape,
                    enabled = count >= 1
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_horizontal_rule_24),
                        contentDescription = "Decrementar"
                    )
                }

                Text(
                    "${count} hora(s)",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                )

                Button(
                    onClick = {
                        count++
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    shape = CircleShape,
                    enabled = count < 24
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Incrementar",
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            TimePickerComponent(addHorario = { it -> horario = it })


//            if (frequencia.isNotEmpty()) {
//                Text(text = "Selecione os horários")
//                LazyColumn {
//                    items(count = frequencia.toInt()) { index ->
//                        TimePickerComponent(
//                            frequencia.toInt(), addHorario = { it ->
//                            horario.add(index, it)
//                        }, selectedHorario = horario.get(index))
//                    }
//                }
//                    TimePickerComponent(frequencia.toInt(), addHorario = { horario.add(it)}, selectedHorario = "Teste")

//            }
            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                onClick = {
//                        val formatter = DateTimeFormatter.ofPattern("HH:mm")
//                        val horarioInicial = LocalTime.parse(horario, formatter)
//                        var proximoHorario = horarioInicial
                        var (horas, minutos) = horario.split(":").map { it.toInt() }
                        var i = 0
                        if (count > 0) {
                            while (horarios.size < (24 / count)) {
                                horarios.add(i, String.format("%02d:%02d", horas, minutos))
                                horas += count
                                if (horas >= 24) {
                                    horas -= 24
                                }
                                i++
                            }
                        } else {
                            horarios.add(i, horario)
                        }

                    medicamentoViewModel.salvarMedicamento(
                        Medicamento(
                            nome = nome,
                            descricao = descricao,
                            dosagem = "${dosagem} ${selectedOptionText}",
                            frequencia = frequencia,
                            horario = horarios
                        )
                    )
                },
                modifier.fillMaxWidth()
            ) {
                Text(text = "Salvar")
            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialWithDialogExample(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    TimePickerDialog(
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(timePickerState) }
    ) {
        TimePicker(
            state = timePickerState,
        )
    }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DialExample(
//    onConfirm: () -> Unit,
//    onDismiss: () -> Unit,
//) {
//    val currentTime = Calendar.getInstance()
//
//    val timePickerState = rememberTimePickerState(
//        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
//        initialMinute = currentTime.get(Calendar.MINUTE),
//        is24Hour = true,
//    )
//
//    Column {
//        TimePicker(
//            state = timePickerState,
//        )
//        Button(onClick = onDismiss) {
//            Text("Dismiss picker")
//        }
//        Button(onClick = onConfirm) {
//            Text("Confirm selection")
//        }
//    }
//}

//@Preview
//@Composable
//fun CreateMedicamentoPreview() {
//    val localContext = LocalContext.current
//    CreateMedicamento(navController = NavController(localContext), modifier = Modifier, medicamentoViewModel = MedicamentoViewModel())
//}