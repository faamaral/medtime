package com.digitaldose.medtime

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.digitaldose.medtime.ui.screens.CreateMedicamento
import com.digitaldose.medtime.ui.screens.HomeScreen
import com.digitaldose.medtime.utils.constants.Routes
import com.digitaldose.medtime.viewmodels.MedicamentoViewModel

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 13/11/2024
 */

@Composable
fun AppNavigation(modifier: Modifier, medicamentoViewModel: MedicamentoViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HOME, builder = {
        composable(Routes.HOME) {
            HomeScreen(navController = navController,modifier = modifier, medicamentoViewModel = medicamentoViewModel)
        }
        composable(Routes.CREATE_MEDICAMENTO) {
            CreateMedicamento(navController = navController, modifier = modifier, medicamentoViewModel = medicamentoViewModel)
        }
        composable(Routes.UPDATE_MEDICAMENTO) {
            // TODO: Colocar aqui chamada para tela de edição de medicamento
        }
        composable(Routes.DELETE_MEDICAMENTO) {
            // TODO: Colocar aqui chamada para exclusão de medicamento
        }
    })
}