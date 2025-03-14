package com.digitaldose.medtime

import CreateEditMedicamentoScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.digitaldose.medtime.ui.screens.HomeScreen
import com.digitaldose.medtime.ui.screens.MenuScreen
import com.digitaldose.medtime.ui.screens.SettingsScreen
import com.digitaldose.medtime.ui.screens.auth.LoginScreen
import com.digitaldose.medtime.ui.screens.auth.SignupScreen
import com.digitaldose.medtime.ui.screens.profile.UserProfileScreen
import com.digitaldose.medtime.ui.screens.profile.UserProfileUpdate
import com.digitaldose.medtime.utils.constants.Routes
import com.digitaldose.medtime.viewmodels.AuthViewModel
import com.digitaldose.medtime.viewmodels.MedicamentoViewModel
import com.digitaldose.medtime.viewmodels.UserViewModel

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 13/11/2024
 */

@Composable
fun AppNavigation(
    modifier: Modifier,
    medicamentoViewModel: MedicamentoViewModel,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HOME, builder = {
        composable(Routes.LOGIN) {
            LoginScreen(Modifier, navController, authViewModel)
        }
        composable(Routes.SIGNUP) {
            SignupScreen(Modifier, navController, authViewModel)
        }
        composable(Routes.HOME, arguments = listOf(navArgument("shouldRefresh") {
            type = NavType.BoolType
            defaultValue = false
        })) { backStackEntry ->
            val shouldRefresh = backStackEntry.arguments?.getBoolean("shouldRefresh") ?: false
            HomeScreen(
                navController = navController,
                modifier = modifier,
                medicamentoViewModel = medicamentoViewModel,
                shouldRefresh = shouldRefresh,
                authViewModel = authViewModel
            )
        }
        composable(
            "${Routes.USER_PROFILE}/{userId}",
            arguments = listOf(navArgument(name = "userId") { type = NavType.StringType })
        ) {
            UserProfileScreen(
                userId = it.arguments?.getString("userId") ?: "",
                navController,
                authViewModel,
                userViewModel
            )
        }
        composable(
            "${Routes.USER_PROFILE_EDIT}/{userId}",
            arguments = listOf(navArgument(name = "userId") { type = NavType.StringType })
        ) {
            UserProfileUpdate(
                userId = it.arguments?.getString("userId") ?: "",
                navController,
                authViewModel,
                userViewModel
            )
        }
        composable(Routes.CREATE_MEDICAMENTO) {
            CreateEditMedicamentoScreen(
                navController = navController,
                medicamentoViewModel = medicamentoViewModel,
                medicamentoId = null
            )
        }
        composable(
            "${Routes.UPDATE_MEDICAMENTO}/{medicamentoId}",
            arguments = listOf(navArgument("medicamentoId") { type = NavType.StringType })
        ) { backStackEntry ->
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
        composable(Routes.SETTINGS) {
            SettingsScreen(navController)
        }
        composable(Routes.MENU) {
            MenuScreen(navController)
        }
    })
}