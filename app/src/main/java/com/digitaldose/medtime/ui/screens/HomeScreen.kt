package com.digitaldose.medtime.ui.screens

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.digitaldose.medtime.database.models.NotificationItem
import com.digitaldose.medtime.database.models.TabBarItem
import com.digitaldose.medtime.services.notification.NotificationAlarmScheduler
import com.digitaldose.medtime.ui.components.AppBar
import com.digitaldose.medtime.ui.components.MedicamentoItem
import com.digitaldose.medtime.ui.components.TabView
import com.digitaldose.medtime.ui.theme.CustomColors
import com.digitaldose.medtime.utils.constants.Routes
import com.digitaldose.medtime.viewmodels.AuthState
import com.digitaldose.medtime.viewmodels.AuthViewModel
import com.digitaldose.medtime.viewmodels.MedicamentoState
import com.digitaldose.medtime.viewmodels.MedicamentoViewModel
import com.google.firebase.auth.FirebaseAuth

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 13/11/2024
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    medicamentoViewModel: MedicamentoViewModel,
    authViewModel: AuthViewModel,
    modifier: Modifier,
    shouldRefresh: Boolean
) {
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> {
                navController.navigate(Routes.LOGIN)
            }
            else -> {}
        }
    }
    val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
    // Observa os medicamentos da ViewModel
    val medicamentos =
        medicamentoViewModel.obterMedicamentosPorUserId(userId).observeAsState(mutableListOf()).value
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

//    Scaffold(
//        topBar = {
//            AppBar(
//                title = "Medicamentos",
//                actions = {
//                    IconButton(onClick = {
//                        navController.navigate(Routes.CREATE_MEDICAMENTO)
//                    }) {
//                        Icon(Icons.Filled.Add, "Adicionar Medicamento")
//                    }
//                    IconButton(onClick = {
//                        navController.navigate("${Routes.USER_PROFILE}/${FirebaseAuth.getInstance().currentUser?.uid}")
//                    }) {
//                        Icon(Icons.Filled.Person, "Perfil do Usuário")
//                    }
//                }
//            )
//        },
//        bottomBar = {
//            TabView(tabBarItens, navController)
//        },
//
//        /*floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
////                    val reminderItem = NotificationItem(
////                        time = Calendar.getInstance().apply {
////                            set(Calendar.HOUR_OF_DAY, 15)
////                            set(Calendar.MINUTE, 47)
////                        }.timeInMillis,
////                        id = 1,
////                    )
////                    notificationAlarmScheduler.schedule(reminderItem)
//                    navController.navigate(Routes.CREATE_MEDICAMENTO)
//                },
//                containerColor = Color.DarkGray,
//                contentColor = Color.White,
//                shape = CircleShape,
//                content = {
//                    Icon(Icons.Filled.Add, contentDescription = "Adicionar Medicamento")
//                }
//            )
//        }*/
//    ) {
        LazyColumn(modifier = Modifier
            .padding()
            .fillMaxSize()) {
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
//    }
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