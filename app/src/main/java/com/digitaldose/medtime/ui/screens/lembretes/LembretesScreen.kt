package com.digitaldose.medtime.ui.screens.lembretes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.digitaldose.medtime.ui.components.AppBar
import com.digitaldose.medtime.ui.components.LembreteItem
import com.digitaldose.medtime.ui.theme.CustomColors
import com.digitaldose.medtime.utils.constants.Routes
import com.digitaldose.medtime.utils.converters.DateConverter
import com.digitaldose.medtime.utils.converters.HoraConverter
import com.digitaldose.medtime.viewmodels.LembreteViewModel

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 21/03/2025
 */

@Composable
fun LembretesScreen(lembreteViewModel: LembreteViewModel, navController: NavController) {
    val lembretes by lembreteViewModel.lembreteLiveData.observeAsState(mutableListOf())

    Scaffold(
        topBar = {
            AppBar(
                title = "Medicamentos",
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Routes.MAIN)
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar para Home")
                    }
                },
            )
        },
    ) {
        LazyColumn(modifier = Modifier.padding(top = it.calculateTopPadding())) {
            itemsIndexed(lembretes) { index, item ->
                LembreteItem(item, lembreteViewModel)
            }
        }
    }


}
