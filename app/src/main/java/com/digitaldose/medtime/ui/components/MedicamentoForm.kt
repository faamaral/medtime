import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.digitaldose.medtime.database.models.Medicamento
import com.digitaldose.medtime.database.models.NotificationItem
import com.digitaldose.medtime.services.notification.NotificationAlarmScheduler
import com.digitaldose.medtime.ui.components.CustomOutlinedTextField
import com.digitaldose.medtime.ui.components.DropdownMenuComponent
import com.digitaldose.medtime.ui.components.IntervaloDialog
import com.digitaldose.medtime.ui.components.TimePickerComponent
import com.digitaldose.medtime.ui.theme.CustomColors
import com.digitaldose.medtime.utils.constants.ListsCadastroMedicamentos
import com.digitaldose.medtime.utils.constants.Routes
import com.digitaldose.medtime.utils.helpers.HorariosHelper
import com.google.common.collect.Lists
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime

/**
 * @author Ramiro Alves <ramiroalves.dev@gmail.com>
 * @since 17/11/2024
 */

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun MedicamentoForm(
    medicamento: Medicamento? = null,
    onSave: (Medicamento) -> Unit
) {

    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var dosagem by remember { mutableStateOf("") }
    var frequencia by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    var intervalo by remember { mutableStateOf("") }
    var horarios by remember { mutableStateOf(mutableListOf<String>()) }

    var expanded by remember { mutableStateOf(false) }
    var expandedFrequencia by remember { mutableStateOf(false) }
    // Unidade de medida de dosagem
    var selectedOptionText by remember { mutableStateOf("") }
    var selectedFrequenciaOptionText by remember { mutableStateOf("") }

    val context = LocalContext.current
    val user = FirebaseAuth.getInstance().currentUser

    var openAlertDialog by remember { mutableStateOf<Boolean>(false) }

    // Atualiza os campos quando o medicamento muda
    LaunchedEffect(medicamento) {
        medicamento?.let {
            nome = it.nome.orEmpty()
            descricao = it.descricao.orEmpty()
            dosagem = it.dosagem.orEmpty()
            frequencia = it.frequencia.orEmpty()
            horario = it.horario?.get(0).toString()
            intervalo = it.intervalo.toString().orEmpty()
            selectedOptionText = it.tipoDosagem.orEmpty()
            selectedFrequenciaOptionText =
                if (it.frequencia?.isBlank() == true) "" else ((if (it.frequencia != ListsCadastroMedicamentos.TiposDosagem[0] && it.frequencia != ListsCadastroMedicamentos.frequencia[2]) ListsCadastroMedicamentos.frequencia[1] else it.frequencia).toString())

        }
    }

    fun alertDialog() {
//        val alertDialog = AlertDialog.Builder(context)
//        alertDialog.setTitle("Adicionar intervalo")
//        alertDialog.setMessage("Deseja excluir o medicamento?")
//            .setPositiveButton("Confirmar") { _, _ ->
//                medicamentoViewModel.deletarMedicamento(medicamento.id)
//                scope.launch(Dispatchers.Main) {
//                    listaMedicamentos.removeAt(index)
//                    navController.navigate(Routes.HOME)
//                    Toast.makeText(context, "Medicamento excluído com sucesso!", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//            .setNegativeButton("Cancelar") { _, _ ->
//                showDialog.value = false
//            }.show()

    }

    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        CustomOutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = "Nome",
//            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.padding(5.dp))
        CustomOutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = "Descrição",
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Row() {
            CustomOutlinedTextField(
                value = dosagem,
                onValueChange = { dosagem = it },
                label = "Dosagem",
                modifier = Modifier.fillMaxWidth(0.6f)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            DropdownMenuComponent(
                options = ListsCadastroMedicamentos.TiposDosagem,
                selectedOption = selectedOptionText,
                onOptionSelected = { selectedOptionText = it },
                label = "Tipo",
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))
        DropdownMenuComponent(
            label = "Frequência",
            options = ListsCadastroMedicamentos.frequencia,
            selectedOption = selectedFrequenciaOptionText,
            onOptionSelected = {
                selectedFrequenciaOptionText = it
                frequencia = selectedFrequenciaOptionText
            },
            modifier = Modifier.fillMaxWidth()
        )
        if (selectedFrequenciaOptionText == ListsCadastroMedicamentos.frequencia[1]) {
            when {
                openAlertDialog -> {
                    IntervaloDialog(
                        onDismiss = { openAlertDialog = false },
                        onConfirm = { novoIntervalo ->
                            intervalo = novoIntervalo.toString()
                            openAlertDialog = false
                        })
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
            CustomOutlinedTextField(
                value = if (intervalo.isNotBlank()) "A cada $intervalo hora(s)" else "",
                onValueChange = {},
                label = "Intervalo",
                placeholder = {
                    Text(text = "Clique no icone para adicionar um intervalo")
                },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { openAlertDialog = true }),
                trailingIcon = {
                    IconButton(onClick = {
                        openAlertDialog = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "Add Intervalo"
                        )
                    }
                }
            )
        }
        if (selectedFrequenciaOptionText.isNotBlank()) {
            Spacer(modifier = Modifier.padding(5.dp))
            TimePickerComponent(
                value = horario,
                addHorario = { it -> horario = it },
                label = if (selectedFrequenciaOptionText == ListsCadastroMedicamentos.frequencia[1]) "Horário Inicial" else "Horário",
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.padding(30.dp))
        Button(
            onClick = {
                if (intervalo.isNotBlank()) {
                    var (hora, minuto) = horario.split(":").map { it.toInt() }
                    var i: Int = 0
                    while (horarios.size < (24 / intervalo.toInt())) {
                        horarios.add(i, String.format("%02d:%02d", hora, minuto))
                        hora += intervalo.toInt()
                        if (hora >= 24) {
                            hora -= 24
                        }
                        i++
                    }
                } else {
                    horarios.add(horario)
                }
                val novoMedicamento = Medicamento(
                    nome = nome,
                    descricao = descricao,
                    dosagem = dosagem,
                    tipoDosagem = selectedOptionText,
                    frequencia = if (frequencia == ListsCadastroMedicamentos.frequencia[1]) "${(24 / intervalo.toInt())} vezes ao dia" else frequencia,
                    intervalo = intervalo.toIntOrNull(),
                    horario = horarios,
                    userId = user?.uid.toString()
                )
                try {
                    val timeInMillis =
                        HorariosHelper.converterHorarioStringParaLong(novoMedicamento.horario!!)
                    timeInMillis.forEachIndexed { index, horario ->
                        val medicamentoNotification = NotificationAlarmScheduler(context)
                        medicamentoNotification.schedule(
                            NotificationItem(
                                time = horario,
                                id = index,
                                medicamento = novoMedicamento
                            )
                        )
                    }
                    Toast.makeText(
                        context,
                        "Medicamento cadastrado com sucesso!",
                        Toast.LENGTH_LONG
                    ).show()
                } catch (e: Exception) {
                    Log.d("Notification", e.message.toString())
                    Toast.makeText(context, "${e.message}", Toast.LENGTH_LONG).show()
                }
                onSave(novoMedicamento)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = CustomColors.RED_BOTTON_MENU,
                contentColor = CustomColors.TextColor
            ),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                "Salvar",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
