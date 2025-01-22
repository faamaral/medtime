import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.digitaldose.medtime.models.Medicamento
import com.digitaldose.medtime.viewmodels.MedicamentoState
import com.digitaldose.medtime.viewmodels.MedicamentoViewModel

/**
 * @author Ramiro Alves <ramiroalves.dev@gmail.com>
 * @since 17/11/2024
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditMedicamentoScreen(
    navController: NavController,
    medicamentoViewModel: MedicamentoViewModel,
    medicamentoId: String?,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val isEditing = medicamentoId != null
    val titulo = if (isEditing) "Editar Medicamento" else "Criar Medicamento"

    // Estados locais para os campos (inicializados vazios)
    var medicamento by remember { mutableStateOf<Medicamento?>(null) }

    // Busca o medicamento pelo ID ao carregar a tela, se for edição
    LaunchedEffect(medicamentoId) {
        medicamentoId?.let { id ->
            medicamento = medicamentoViewModel.obterMedicamentoPorId(id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titulo) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) { // Substitua "home" pela rota da tela inicial
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar para Home")
                    }
                }
            )
        },
        content = { padding ->
//            if (isEditing && medicamentoViewModel.medicamentoState.observeAsState().value is MedicamentoState.Loading) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(padding),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.TopStart
                ) {
                    MedicamentoForm(
                        medicamento = medicamento,
                        onSave = { medicamentoAtualizado ->
                            if (isEditing) {
                                medicamentoAtualizado.id = medicamentoId!!
                                medicamentoViewModel.atualizarMedicamento(medicamentoAtualizado)
                            } else {
                                medicamentoViewModel.salvarMedicamento(medicamentoAtualizado, context)
                            }
                            navController.popBackStack()
                        }
                    )
                }
//            }
        }
    )
}