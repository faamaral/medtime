package com.digitaldose.medtime.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.digitaldose.medtime.database.models.LembreteEntity
import com.digitaldose.medtime.ui.theme.CustomColors
import com.digitaldose.medtime.utils.converters.DateConverter
import com.digitaldose.medtime.utils.converters.HoraConverter
import com.digitaldose.medtime.viewmodels.LembreteViewModel

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 27/03/2025
 */

@Composable
fun LembreteItem(
    item: LembreteEntity,
    lembreteViewModel: LembreteViewModel
) {
    var tomou by remember { mutableStateOf(item.acao) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = CustomColors.CARD_CONTAINER_COLOR_MEDICAMENTO_ITEM
        ),
        border = CardDefaults.outlinedCardBorder(
            enabled = true
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.CenterHorizontally)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                Text(
                    text = item.titulo,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = item.descricao,
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.SemiBold)

                        ) {
                            append("Data: ")
                        }
                        append(DateConverter.convertMillisToDate(item.data))
                    },
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append("Horário: ")
                        }
                        append(HoraConverter.converterMillisToString(item.hora))
                    },
                )
            }
            Column() {
                Checkbox(
                    checked = tomou,
                    onCheckedChange = {
                        tomou = it

                        lembreteViewModel.atualizarLembrete(
                            item.copy(
                                acao = tomou
                            )
                        )
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Green,
                        uncheckedColor = Color.LightGray
                    )
                )
            }
        }
    }
}