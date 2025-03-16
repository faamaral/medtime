package com.digitaldose.medtime.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.digitaldose.medtime.database.models.TabBarItem
import com.digitaldose.medtime.ui.components.AppBar
import com.digitaldose.medtime.ui.components.TabView
import com.digitaldose.medtime.utils.constants.Routes
import com.digitaldose.medtime.viewmodels.AuthState
import com.digitaldose.medtime.viewmodels.AuthViewModel
import com.digitaldose.medtime.viewmodels.MedicamentoViewModel
import com.google.firebase.auth.FirebaseAuth

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 14/03/2025
 */

@Composable
fun MainScreen(
    navController: NavController,
    medicamentoViewModel: MedicamentoViewModel,
    authViewModel: AuthViewModel
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
    val homeTab =
        TabBarItem(
            Routes.HOME,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        )
    val menuTab =
        TabBarItem(
            Routes.MENU,
            selectedIcon = Icons.Filled.Menu,
            unselectedIcon = Icons.Outlined.Menu
        )
    val settingsTab = TabBarItem(
        Routes.SETTINGS,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )

    val tabBarItens = listOf(homeTab, menuTab, settingsTab)

    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            when (selectedTab) {
                0 -> AppBar(
                    title = "Medicamentos",
                    actions = {
                        IconButton(onClick = {
                            navController.navigate(Routes.CREATE_MEDICAMENTO)
                        }) {
                            Icon(Icons.Filled.Add, "Adicionar Medicamento")
                        }
                        IconButton(onClick = {
                            navController.navigate("${Routes.USER_PROFILE}/${FirebaseAuth.getInstance().currentUser?.uid}")
                        }) {
                            Icon(Icons.Filled.Person, "Perfil do Usuário")
                        }
                    }
                )
                1 -> AppBar(
                    title = "Menu",
                )
                2 -> AppBar(
                    title = "Configurações",
                )
            }
        },
        bottomBar = {
            TabView(tabBarItens, selectedTab = {
                selectedTab = it
            })
        }
    ) {
        Surface(modifier = Modifier.padding(top = it.calculateTopPadding())) {
            when (selectedTab) {
                0 -> HomeScreen(
                    navController = navController,
                    medicamentoViewModel = medicamentoViewModel,
                    authViewModel = authViewModel,
                    modifier = Modifier,
                    shouldRefresh = false
                )
                1 -> MenuScreen(navController)
                2 -> SettingsScreen(navController)
            }
        }


    }
}