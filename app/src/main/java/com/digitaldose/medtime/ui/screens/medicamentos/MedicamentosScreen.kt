package com.digitaldose.medtime.ui.screens.medicamentos

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.digitaldose.medtime.services.notification.NotificationAlarmScheduler
import com.digitaldose.medtime.ui.components.AppBar
import com.digitaldose.medtime.ui.components.LembreteItem
import com.digitaldose.medtime.ui.components.MedicamentoItem
import com.digitaldose.medtime.ui.components.TabView
import com.digitaldose.medtime.utils.constants.Routes
import com.digitaldose.medtime.viewmodels.AuthViewModel
import com.digitaldose.medtime.viewmodels.LembreteViewModel
import com.digitaldose.medtime.viewmodels.MedicamentoViewModel
import com.google.firebase.auth.FirebaseAuth

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 27/03/2025
 */

@Composable
fun MedicamentosScreen(
    navController: NavController,
    medicamentoViewModel: MedicamentoViewModel,
    shouldRefresh: Boolean,
) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
    // Observa os medicamentos da ViewModel
    val medicamentos =
        medicamentoViewModel.obterMedicamentosPorUserId(userId)
            .observeAsState(mutableListOf()).value
    val medicamentoState = medicamentoViewModel.medicamentoState.observeAsState()
    val context = LocalContext.current
    val notificationAlarmScheduler by lazy {
        NotificationAlarmScheduler(context)
    }

    if (shouldRefresh) {
        LaunchedEffect(Unit) {
            medicamentoViewModel.obterMedicamentosPorUserId(userId)
        }
    }

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
            AppBar(
                title = "Medicamentos",
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Routes.MAIN)
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar para Home")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Routes.CREATE_MEDICAMENTO)
                    }) {
                        Icon(Icons.Filled.Add, "Adicionar Medicamento")
                    }
                }
            )
        },

        /*floatingActionButton = {
            FloatingActionButton(
                onClick = {
//                    val reminderItem = NotificationItem(
//                        time = Calendar.getInstance().apply {
//                            set(Calendar.HOUR_OF_DAY, 15)
//                            set(Calendar.MINUTE, 47)
//                        }.timeInMillis,
//                        id = 1,
//                    )
//                    notificationAlarmScheduler.schedule(reminderItem)
                    navController.navigate(Routes.CREATE_MEDICAMENTO)
                },
                containerColor = Color.DarkGray,
                contentColor = Color.White,
                shape = CircleShape,
                content = {
                    Icon(Icons.Filled.Add, contentDescription = "Adicionar Medicamento")
                }
            )
        }*/
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
        ) {
            itemsIndexed(medicamentos) { index, item ->
                MedicamentoItem(
                    index = index,
                    listaMedicamentos = medicamentos,
                    medicamento = item,
                    navController = navController,
                    medicamentoViewModel = medicamentoViewModel,
                    context = context,
//                    onClick = {
//                    navController.navigate("update_medicamento/${item.id}")
//                }
                )
            }
        }


    }
}