import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.digitaldose.medtime.models.Medicamento

/**
 * @author Ramiro Alves <ramiroalves.dev@gmail.com>
 * @since 17/11/2024
*/

@Composable
fun MedicamentoForm(
    medicamento: Medicamento? = null,
    onSave: (Medicamento) -> Unit
) {

    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var dosagem by remember { mutableStateOf("") }
    var frequencia by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }

    // Atualiza os campos quando o medicamento muda
    LaunchedEffect(medicamento) {
        medicamento?.let {
            nome = it.nome.orEmpty()
            descricao = it.descricao.orEmpty()
            dosagem = it.dosagem.orEmpty()
            frequencia = it.frequencia.orEmpty()
            horario = it.horario?.joinToString(", ").orEmpty()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = dosagem,
            onValueChange = { dosagem = it },
            label = { Text("Dosagem") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = frequencia,
            onValueChange = { frequencia = it },
            label = { Text("Frequência") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = horario,
            onValueChange = { horario = it },
            label = { Text("Horário") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                val novoMedicamento = Medicamento(
                    id = medicamento?.id ?: "", // Retorna o ID atual se for edição
                    nome = nome,
                    descricao = descricao,
                    dosagem = dosagem,
                    frequencia = frequencia,
                    horario = horario.split(", ").map { it.trim() }
                )
                onSave(novoMedicamento)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Salvar")
        }
    }
}
