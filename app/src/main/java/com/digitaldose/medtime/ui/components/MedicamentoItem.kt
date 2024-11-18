package com.digitaldose.medtime.ui.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.digitaldose.medtime.models.Medicamento
import com.digitaldose.medtime.utils.constants.Routes

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 13/11/2024
 */

@Composable
fun MedicamentoItem(medicamento: Medicamento, navController: NavController, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
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
                IconButton(
                    onClick = {
                        navController.navigate(Routes.DELETE_MEDICAMENTO)
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