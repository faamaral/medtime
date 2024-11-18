package com.digitaldose.medtime.ui.components

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.digitaldose.medtime.models.Medicamento
import com.digitaldose.medtime.utils.constants.Routes
import com.digitaldose.medtime.viewmodels.MedicamentoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 13/11/2024
 */

@Composable
fun MedicamentoItem(
    index: Int,
    listaMedicamentos: MutableList<Medicamento>,
    medicamento: Medicamento,
    navController: NavController,
    medicamentoViewModel: MedicamentoViewModel,
    context: Context,
//    onClick: () -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    fun alertDialog() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Excluir")
        alertDialog.setMessage("Deseja excluir o medicamento?")
            .setPositiveButton("Sim") { _, _ ->
                medicamentoViewModel.deletarMedicamento(medicamento.id)
                scope.launch(Dispatchers.Main) {
                    listaMedicamentos.removeAt(index)
                    navController.navigate(Routes.HOME)
                    Toast.makeText(context, "Medicamento excluído com sucesso!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Não") { _, _ ->
//                showDialog.value = false
            }.show()
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
//            .clickable { onClick() }
        ,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("${medicamento.nome}")
            Text("${medicamento.descricao}")
            Text("Dosagem: ${medicamento.dosagem}")
            Text("Frequência: ${medicamento.frequencia}")
            Text("Horarios: ${medicamento.horario.toString()}")
            Row() {
                IconButton(
                    onClick = {
                        navController.navigate("${Routes.UPDATE_MEDICAMENTO}/${medicamento.id}")
                    }, colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.Blue
                    )
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = "Editar")
                }
//                if (showDialog.value) {
//                    DeleteItem1(
//                        onConfirmButton = {
//                            medicamentoViewModel.deletarMedicamento(medicamento.id)
//                        },
//                        onDismissButton = {
//                            showDialog.value = false
//                        },
//                        setShowDialog = {showDialog.value = it}
//                    )
//                }
                IconButton(
                    onClick = {
                        alertDialog()
                    }, colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.Red
                    )
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Excluir")
                }
            }
        }
    }
}