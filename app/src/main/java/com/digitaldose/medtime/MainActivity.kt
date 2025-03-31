package com.digitaldose.medtime

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.digitaldose.medtime.database.MedtimeDatabase
import com.digitaldose.medtime.ui.theme.MedtimeTheme
import com.digitaldose.medtime.viewmodels.AuthViewModel
import com.digitaldose.medtime.viewmodels.LembreteViewModel
import com.digitaldose.medtime.viewmodels.MedicamentoViewModel
import com.digitaldose.medtime.viewmodels.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    private val viewModel: MedicamentoViewModel by viewModel()
    private val authViewModel: AuthViewModel by viewModel()
    private val userViewModel: UserViewModel by viewModel()
    private val lembreteViewModel: LembreteViewModel by viewModel()
    companion object {
        var dataBase: MedtimeDatabase? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        try {
            dataBase = Room.databaseBuilder(applicationContext, MedtimeDatabase::class.java, "medtime-db").build()
        }
        catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }

        setContent {
//            viewModel.gerarDadosFalsos()
            MedtimeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding),
                        medicamentoViewModel = viewModel,
                        authViewModel = authViewModel,
                        userViewModel = userViewModel,
                        lembreteViewModel = lembreteViewModel
                    )
                }
            }
        }
    }
}