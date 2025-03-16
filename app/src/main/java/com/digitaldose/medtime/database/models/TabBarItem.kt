package com.digitaldose.medtime.database.models

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 22/02/2025
 */

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)
