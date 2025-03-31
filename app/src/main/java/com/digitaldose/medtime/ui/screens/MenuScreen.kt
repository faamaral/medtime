package com.digitaldose.medtime.ui.screens

import android.widget.ListView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.ColumnInfo
import com.digitaldose.medtime.utils.constants.Routes

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 14/03/2025
 */

@Composable
fun MenuScreen(navController: NavController) {
    Scaffold {
        Column(modifier = Modifier.padding(top = it.calculateTopPadding())) {
            LazyColumn {
                item {
                    Row(modifier = Modifier.clickable {
                        navController.navigate(Routes.LEMBRETES)
                    }) {
                        Text(
                            text = "Lembretes",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(16.dp)

                        )
                    }
                }

                item {
                    Row(modifier = Modifier.clickable {
                        navController.navigate(Routes.MEDICAMENTOS)
                    }) {
                        Text(
                            text = "Medicamentos",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(16.dp)

                        )
                    }
                }
            }
        }
    }
}