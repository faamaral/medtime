package com.digitaldose.medtime.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.digitaldose.medtime.R
import com.digitaldose.medtime.database.models.User
import com.digitaldose.medtime.ui.components.CustomOutlinedTextField
import com.digitaldose.medtime.ui.theme.CustomColors
import com.digitaldose.medtime.viewmodels.AuthState
import com.digitaldose.medtime.viewmodels.AuthViewModel
import com.digitaldose.medtime.utils.constants.Routes

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 29/10/2024
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                navController.navigate(Routes.MAIN)
            }

            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    (authState.value as AuthState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Cadastrar", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        CustomOutlinedTextField(
            value = nome,
            onValueChange = {
                nome = it
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            label = "Nome",
        )
        CustomOutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = "Email",

            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        CustomOutlinedTextField(
            value = password, onValueChange = {
                password = it
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            label = "Senha",
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    R.drawable.visibility_24px
                else R.drawable.visibility_off_24px

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = painterResource(image), description)
                }
            }
        )
        CustomOutlinedTextField(value = confirmPassword, onValueChange = {
            confirmPassword = it
        },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            label = "Confirmar Senha",
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    R.drawable.visibility_24px
                else R.drawable.visibility_off_24px

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = painterResource(image), description)
                }
            }
        )
        Button(
            onClick = {
                if (password == confirmPassword) {
                    val user = User(
                        id = "",
                        email = email,
                        password = password,
                        name = nome
                    )
                    authViewModel.signup(user)
                } else {
                    //
                }
            },
            enabled = authState.value != AuthState.Loading,
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
                "Cadastrar",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Já tem uma conta?")
            TextButton({
                navController.navigate(Routes.LOGIN)
            }) {
                Text("Entre")
            }
        }
    }

}