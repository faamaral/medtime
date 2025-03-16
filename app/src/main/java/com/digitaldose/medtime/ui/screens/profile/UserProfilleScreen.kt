package com.digitaldose.medtime.ui.screens.profile

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.digitaldose.medtime.database.models.Medicamento
import com.digitaldose.medtime.database.models.NotificationItem
import com.digitaldose.medtime.database.models.User
import com.digitaldose.medtime.database.repositories.UserRepository
import com.digitaldose.medtime.services.notification.NotificationAlarmScheduler
import com.digitaldose.medtime.ui.components.AppBar
import com.digitaldose.medtime.ui.components.CustomOutlinedTextField
import com.digitaldose.medtime.ui.components.DropdownMenuComponent
import com.digitaldose.medtime.ui.components.IntervaloDialog
import com.digitaldose.medtime.ui.components.TimePickerComponent
import com.digitaldose.medtime.ui.theme.CustomColors
import com.digitaldose.medtime.utils.constants.Genero
import com.digitaldose.medtime.utils.constants.ListsCadastroMedicamentos
import com.digitaldose.medtime.utils.constants.Routes
import com.digitaldose.medtime.utils.helpers.HorariosHelper
import com.digitaldose.medtime.viewmodels.AuthViewModel
import com.digitaldose.medtime.viewmodels.UserViewModel

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 11/03/2025
 */
@Composable
fun UserProfileScreen(
    userId: String,
    navController: NavController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel
) {

    val user = userViewModel.getUser(userId).observeAsState().value


    Scaffold(
        topBar = {
            AppBar(
                title = "Perfil",
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.MAIN) }) { // Substitua "home" pela rota da tela inicial
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar para Home"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("${Routes.USER_PROFILE_EDIT}/${userId}")
                    }) {
                        Icon(Icons.Filled.Edit, "Editar Perfil")
                    }
                    IconButton(onClick = {
                        authViewModel.logout()
                        navController.navigate(Routes.LOGIN)
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, "Sair")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize(),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(1f).padding(8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier
                            .padding(28.dp)
                            .drawBehind { drawCircle(color = Color.Cyan, radius = this.size.maxDimension) },
                        text = user?.name?.first().toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = user?.name.toString(),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(5.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),

                    ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append("Data de Nascimento: ")
                            }
                            append((user?.dataNascimento ?: "Não informado").toString())
                        },
                        modifier = Modifier
                            .padding(8.dp),
//                text = "Data de Nascimento: " + user?.dataNascimento.let {
//                    "Não informado"
//                },
//                fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
//                text = "Sexo: ${user?.sexo.toString().let {
//                    "Não informado"
//                }}",
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append("Sexo: ")
                            }
                            append((user?.sexo ?: "Não informado").toString())
                        },
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
//                text = "Altura: ${
//                    user?.altura.toString().let {
//                        "Não informado"
//                    }
//                }",
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                    append("Altura (cm): ")
                                }
                                append((user?.altura ?: "Não informado").toString())
                            },
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
//                text = "Peso: ${
//                    user?.peso.toString().let {
//                        "Não informado"
//                    }
//                }",
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                    append("Peso (kg): ")
                                }
                                append((user?.peso ?: "Não informado").toString())
                            },
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }

                }


            }

        }
    }
}