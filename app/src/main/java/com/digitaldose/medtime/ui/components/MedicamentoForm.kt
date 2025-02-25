import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.digitaldose.medtime.models.Medicamento
import com.digitaldose.medtime.ui.components.CustomOutlinedTextField
import com.digitaldose.medtime.ui.theme.CustomColors

/**
 * @author Ramiro Alves <ramiroalves.dev@gmail.com>
 * @since 17/11/2024
 */

@SuppressLint("MutableCollectionMutableState")
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
//    var horarios by remember { mutableStateOf(mutableListOf<String>()) }

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

    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        CustomOutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = "Nome",
//            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.padding(5.dp))
        CustomOutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = "Descrição",
        )
        Spacer(modifier = Modifier.padding(5.dp))
        CustomOutlinedTextField(
            value = dosagem,
            onValueChange = { dosagem = it },
            label = "Dosagem",
        )
        Spacer(modifier = Modifier.padding(5.dp))
        CustomOutlinedTextField(
            value = frequencia,
            onValueChange = { frequencia = it },
            label = "Frequência",
        )
        Spacer(modifier = Modifier.padding(5.dp))
        CustomOutlinedTextField(
            value = horario,
            onValueChange = { horario = it },
            label = "Horário",
        )
        Spacer(modifier = Modifier.padding(30.dp))
        Button(
            onClick = {
                val novoMedicamento = Medicamento(
                    nome = nome,
                    descricao = descricao,
                    dosagem = dosagem,
                    frequencia = frequencia,
                    horario = horario.split(", ").map { it.trim() }
                )
                onSave(novoMedicamento)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = CustomColors.RED_BOTTON_MENU,
                contentColor = CustomColors.TextColor
            ),
            modifier = Modifier.fillMaxWidth(0.9f).padding(16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Cadastrar", fontWeight = FontWeight.SemiBold, fontSize = 20.sp, modifier = Modifier.padding(8.dp))
        }
    }
}
