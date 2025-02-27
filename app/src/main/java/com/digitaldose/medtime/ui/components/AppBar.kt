package com.digitaldose.medtime.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.digitaldose.medtime.ui.theme.CustomColors
import com.digitaldose.medtime.utils.constants.Routes

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 24/02/2025
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String,  actions: @Composable() (RowScope.() -> Unit) = {}, navigationIcon: @Composable () -> Unit = {}) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CustomColors.RED_APPBAR,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),
        actions = actions,
        navigationIcon = navigationIcon
    )

}