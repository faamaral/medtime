package com.digitaldose.medtime.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.digitaldose.medtime.database.models.TabBarItem
import com.digitaldose.medtime.ui.components.AppBar
import com.digitaldose.medtime.ui.components.TabView
import com.digitaldose.medtime.utils.constants.Routes

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 14/03/2025
 */

@Composable
fun MenuScreen(navController: NavController)
{
    val homeTab =
        TabBarItem(Routes.HOME, selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
    val menuTab =
        TabBarItem(Routes.MENU, selectedIcon = Icons.Filled.Menu, unselectedIcon = Icons.Outlined.Menu)
    val settingsTab = TabBarItem(
        Routes.SETTINGS,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )

    val tabBarItens = listOf(homeTab, menuTab, settingsTab)
    Scaffold(
        topBar = {
            AppBar(
                title = "Menu",
            )
        },
        bottomBar = {
            TabView(tabBarItems = tabBarItens, navController = navController)
        }
    ) {
        Text(modifier = Modifier.padding(top = it.calculateTopPadding()), text = "Essa vai ser a tela de menu")

    }
}