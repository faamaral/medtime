package com.digitaldose.medtime.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.digitaldose.medtime.models.TabBarItem
import com.digitaldose.medtime.ui.theme.CustomColors

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 22/02/2025
 */

@Composable
fun TabView(tabBarItems: List<TabBarItem>, navController: NavController) {
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    NavigationBar(containerColor = CustomColors.RED_BOTTON_MENU) {
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    navController.navigate(tabBarItem.title)
                },
                icon = {
                    TabBarIconView(
                        isSelected = selectedTabIndex == index,
                        selectedIcon = tabBarItem.selectedIcon,
                        unselectedIcon = tabBarItem.unselectedIcon,
                        title = tabBarItem.title,
                        badgeAmount = tabBarItem.badgeAmount
                    )
                },
//                label = {
//                    Text(tabBarItem.title.uppercase())
//                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = CustomColors.RED_BOTTON_MENU,
                    selectedTextColor = CustomColors.RED_BOTTON_MENU,
                    indicatorColor = Color.White,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White
                )

            )
        }
    }
}

@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(count = badgeAmount) }) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = if (isSelected) {
                    selectedIcon
                } else {
                    unselectedIcon
                }, contentDescription = title
            )
            Text(text = title.uppercase())
        }

    }
}

@Composable
fun TabBarBadgeView(count: Int? = null) {
    if (count != null) {
        Badge {
            Text(text = count.toString())
        }
    }
}
