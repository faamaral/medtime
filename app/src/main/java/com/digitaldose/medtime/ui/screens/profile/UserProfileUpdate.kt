package com.digitaldose.medtime.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.digitaldose.medtime.database.repositories.UserRepository
import com.digitaldose.medtime.ui.components.AppBar
import com.digitaldose.medtime.ui.components.CustomOutlinedTextField
import com.digitaldose.medtime.ui.components.DatePickerComponent
import com.digitaldose.medtime.ui.components.DropdownMenuComponent
import com.digitaldose.medtime.ui.theme.CustomColors
import com.digitaldose.medtime.utils.constants.Genero
import com.digitaldose.medtime.utils.constants.Routes
import com.digitaldose.medtime.viewmodels.AuthViewModel
import com.digitaldose.medtime.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuth

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 13/03/2025
 */
@Composable
fun UserProfileUpdate(
    userId: String,
    navController: NavController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel
) {
    val user = userViewModel.getUser(userId).observeAsState().value

    var name by remember { mutableStateOf("") }
    var dataNascimento by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }

    var selectedOptionText by remember { mutableStateOf("") }

    val context = LocalContext.current

    LaunchedEffect(user) {
        user?.let {
            name = it.name
            dataNascimento =
                it.dataNascimento ?: ""
            sexo = it.sexo ?: ""
            selectedOptionText = it.sexo ?: ""
            altura = if (it.altura.toString() == "null") "" else it.altura.toString()
            peso = if (it.peso.toString() == "null") "" else it.peso.toString()
        }
    }
    Scaffold(
        topBar = {
            AppBar(
                title = "Atualizar Perfil",
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("${Routes.USER_PROFILE}/${FirebaseAuth.getInstance().currentUser?.uid}") }) { // Substitua "home" pela rota da tela inicial
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar para Home"
                        )
                    }
                },
//                actions = {
//                    IconButton(onClick = {
//                        navController.navigate(Routes.USER_PROFILE_EDIT)
//                    }) {
//                        Icon(Icons.Filled.Edit, "Editar Perfil")
//                    }
//                    IconButton(onClick = {
//                        authViewModel.logout()
//                        navController.navigate(Routes.LOGIN)
//                    }) {
//                        Icon(Icons.AutoMirrored.Filled.ExitToApp, "Sair")
//                    }
//                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomOutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = "Nome",
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(
                modifier = Modifier.padding(8.dp)
            )
            DatePickerComponent(
                value = dataNascimento,
                addData = { dataNascimento = it },
                label = "Data de Nascimento",
                modifier = Modifier
            )
            Spacer(
                modifier = Modifier.padding(8.dp)
            )
            DropdownMenuComponent(
                options = Genero.Generos,
                selectedOption = selectedOptionText,
                onOptionSelected = { selectedOptionText = it },
                label = "Sexo",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(
                modifier = Modifier.padding(8.dp)
            )

            CustomOutlinedTextField(
                value = altura,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() } && newValue.length <= 3) {
                        altura = newValue
                    }
                },
                label = "Altura (cm)",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(
                modifier = Modifier.padding(8.dp)
            )
            CustomOutlinedTextField(
                value = peso,
                onValueChange = { newValue ->
                    val valueFormatado = newValue.replace(",", ".")
                    val regex = Regex("^\\d{0,3}([.]\\d{0,2})?$")
                    if (valueFormatado.isBlank() || valueFormatado.matches(regex)) {
                        peso = valueFormatado
                    }
                },
                label = "Peso (kg)",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(
                modifier = Modifier.padding(8.dp)
            )

            Button(
                onClick = {
                    val userRepository = UserRepository()
                    if (user != null) {
                        userRepository.updateUser(
                            userId, user.copy(
                                name = name,
                                dataNascimento = dataNascimento,
                                sexo = selectedOptionText,
                                altura = altura.toIntOrNull(),
                                peso = peso.toDouble()
                            )
                        ).addOnSuccessListener {
                            Toast.makeText(
                                context,
                                "Usuário atualizado com sucesso",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate("${Routes.USER_PROFILE}/${FirebaseAuth.getInstance().currentUser?.uid}")
                        }
                            .addOnFailureListener {
                                Toast.makeText(
                                    context,
                                    "Erro ao atualizar usuário",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomColors.RED_BOTTON_MENU,
                    contentColor = CustomColors.TextColor
                ),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Salvar",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }

        }
    }
}