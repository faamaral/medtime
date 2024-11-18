package com.digitaldose.medtime

import CreateEditMedicamentoScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
        composable(Routes.HOME, arguments = listOf(navArgument("shouldRefresh") {
            type = NavType.BoolType
            defaultValue = false
        })) { backStackEntry ->
            val shouldRefresh = backStackEntry.arguments?.getBoolean("shouldRefresh") ?: false
            HomeScreen(navController = navController,modifier = modifier, medicamentoViewModel = medicamentoViewModel, shouldRefresh = shouldRefresh)
        }
        composable(Routes.CREATE_MEDICAMENTO) {
            CreateEditMedicamentoScreen(navController = navController,medicamentoViewModel = medicamentoViewModel,medicamentoId = null)
        }
        composable("${Routes.UPDATE_MEDICAMENTO}/{medicamentoId}", arguments = listOf(navArgument("medicamentoId") { type = NavType.StringType })) {
                backStackEntry ->
            val medicamentoId = backStackEntry.arguments?.getString("medicamentoId")
            CreateEditMedicamentoScreen(
                navController = navController,
                medicamentoViewModel = medicamentoViewModel,
                medicamentoId = medicamentoId
            )
        }
        composable(Routes.DELETE_MEDICAMENTO) {
            // TODO: Colocar aqui chamada para exclusão de medicamento
        }
    })
}