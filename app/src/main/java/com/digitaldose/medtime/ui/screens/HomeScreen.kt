package com.digitaldose.medtime.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.digitaldose.medtime.ui.components.MedicamentoItem
import com.digitaldose.medtime.viewmodels.MedicamentoState
import com.digitaldose.medtime.viewmodels.MedicamentoViewModel

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 13/11/2024
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, medicamentoViewModel: MedicamentoViewModel, modifier: Modifier) {
    // Observa os medicamentos da ViewModel
    val medicamentos = medicamentoViewModel.obterMedicamentos().observeAsState(mutableListOf()).value
    val medicamentoState = medicamentoViewModel.medicamentoState.observeAsState()
    val context = LocalContext.current

//    LaunchedEffect(medicamentoState.value) {
//        if (medicamentoState.value == MedicamentoState.Init) {
//            medicamentoViewModel.obterMedicamentos()
//            if (medicamentos.isEmpty()) {
//                // TODO: gerar mensagem de tela sem dados
//            }
//        }
//
//    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Medicamentos") }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(top=it.calculateTopPadding()).fillMaxSize()) {
            itemsIndexed(medicamentos) {index, item ->
                MedicamentoItem(index = index, listaMedicamentos = medicamentos, medicamento = item, navController = navController, medicamentoViewModel = medicamentoViewModel, context = context)

            }
        }
    }
}

//@Composable
//fun MedicamentoItem(medicamento: Medicamento) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
////        elevation = 4.dp
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "Nome: ${medicamento.nome}", style = MaterialTheme.typography.titleMedium)
//            Text(text = "Dosagem: ${medicamento.dosagem}")
//            Text(text = "Frequência: ${medicamento.frequencia}")
//            Text(text = "Horários: ${medicamento.horarios.joinToString(", ")}")
//        }
//    }
//}