package com.digitaldose.medtime.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitaldose.medtime.MainActivity
import com.digitaldose.medtime.database.dao.MedicamentoDao
import com.digitaldose.medtime.database.models.HorariosEntity
import com.digitaldose.medtime.database.models.Medicamento
import com.digitaldose.medtime.database.models.MedicamentoEntity
import com.digitaldose.medtime.database.models.NotificationItem
import com.digitaldose.medtime.database.repositories.MedicamentoRepository
import com.digitaldose.medtime.services.notification.Notification
import com.digitaldose.medtime.services.notification.NotificationAlarmScheduler
import com.digitaldose.medtime.utils.constants.ListsCadastroMedicamentos
import com.digitaldose.medtime.utils.helpers.HorariosHelper
import com.google.firebase.firestore.FirebaseFirestoreException
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * ViewModel para a entidade Medicamento.
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 11/11/2024
 */

class MedicamentoViewModel : ViewModel() {
    private val medicamentoRepository = MedicamentoRepository()
    private val _medicamentoState = MutableLiveData<MedicamentoState>()
    val medicamentoState: LiveData<MedicamentoState> = _medicamentoState
    private val _medicamentosLiveData = MutableLiveData<MutableList<Medicamento>>()
    val medicamentosLiveData: LiveData<MutableList<Medicamento>> = _medicamentosLiveData

    init {
        _medicamentoState.value = MedicamentoState.Init
    }

    fun salvarMedicamento(medicamento: Medicamento, context: Context) {
        _medicamentoState.value = MedicamentoState.Loading
//        val medicamentoLocal = MedicamentoEntity(
//            id = medicamento.id,
//            nome = medicamento.nome!!,
//            descricao = medicamento.descricao!!,
//            dosagem = medicamento.dosagem!!,
//            tipoDosagem = medicamento.tipoDosagem!!,
//            frequencia = medicamento.frequencia!!,
//            intervalo = medicamento.intervalo!!,
////            horario = medicamento.horario!!,
//            dataCriacao = System.currentTimeMillis(),
//            dataAtualizacao = System.currentTimeMillis()
//        )

//        MainActivity.dataBase?.medicamentoDao()?.insert(medicamentoLocal)
//        medicamento.horario?.forEach { horario ->
//            val hora = HorariosEntity(
//                horario = horario,
//                medicamentoId = medicamentoLocal.id,
//                dataCriacao = System.currentTimeMillis(),
//                dataAtualizacao = System.currentTimeMillis(),
//            )
//            MainActivity.dataBase?.horariosDao()?.insert(hora)
//        }
        try {
            medicamentoRepository.createMedicamento(medicamento).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Medicamento salvo com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        context,
                        "Erro ao salvar medicamento ${it.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            medicamentoRepository.createMedicamento(medicamento).addOnSuccessListener {
                Toast.makeText(context, "Medicamento salvo com sucesso!", Toast.LENGTH_SHORT).show()
//            try {
//                val timeInMillis = HorariosHelper.converterHorarioStringParaLong(medicamento.horario!!)
//                timeInMillis.forEachIndexed { index, horario ->
//                    val medicamentoNotification = NotificationAlarmScheduler(context)
//                    medicamentoNotification.schedule(
//                        NotificationItem(
//                            time = horario,
//                            id = index,
//                            medicamento = medicamento
//                        )
//                    )
//                }
//            } catch (e: Exception) {
//                Log.d("Notification", e.message.toString())
//               Toast.makeText(context, "${e.message}", Toast.LENGTH_LONG).show()
//            }

                _medicamentoState.value = MedicamentoState.Done
            }.addOnFailureListener {
                _medicamentoState.value = MedicamentoState.Error(it.message ?: "Erro desconhecido")
                Toast.makeText(
                    context,
                    "Erro ao salvar medicamento ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: FirebaseFirestoreException) {
            Toast.makeText(context, "Erro ao salvar medicamento ${e.message}", Toast.LENGTH_LONG)
                .show()
        }

    }

    fun atualizarMedicamento(medicamento: Medicamento) {
        _medicamentoState.value = MedicamentoState.Loading
        medicamentoRepository.updateMedicamento(medicamento).addOnSuccessListener {
            _medicamentoState.value = MedicamentoState.Done
        }.addOnFailureListener { exception ->
            _medicamentoState.value =
                MedicamentoState.Error(exception.message ?: "Erro desconhecido")
        }
    }

    fun deletarMedicamento(documentId: String) {
        _medicamentoState.value = MedicamentoState.Loading
        medicamentoRepository.deleteMedicamento(documentId).addOnSuccessListener {
            _medicamentoState.value = MedicamentoState.Done

            _medicamentoState.value = MedicamentoState.Init
        }.addOnFailureListener {
            _medicamentoState.value = MedicamentoState.Error(it.message ?: "Erro desconhecido")
        }
    }

    fun obterMedicamentos(): LiveData<MutableList<Medicamento>> {
        _medicamentoState.postValue(MedicamentoState.Loading)
        medicamentoRepository.getMedicamentos().addOnSuccessListener {
            val medicamentos: MutableList<Medicamento> = mutableListOf()
            for (item in it.documents) {
                val med = item.toObject(Medicamento::class.java)
                medicamentos.add(med!!)
            }
            _medicamentosLiveData.postValue(medicamentos)
        }.addOnFailureListener {
            _medicamentoState.postValue(
                MedicamentoState.Error(
                    it.message ?: "Erro desconhecido"
                )
            )
        }
        return medicamentosLiveData
    }

    fun obterMedicamentosPorUserId(userId: String): LiveData<MutableList<Medicamento>> {
        _medicamentoState.postValue(MedicamentoState.Loading)
        medicamentoRepository.getMedicamentosByUserId(userId).addOnSuccessListener {
            val medicamentos: MutableList<Medicamento> = mutableListOf()
            for (item in it.documents) {
                val med = item.toObject(Medicamento::class.java)
                medicamentos.add(med!!)
            }
            _medicamentosLiveData.postValue(medicamentos)
        }.addOnFailureListener {
            _medicamentoState.postValue(
                MedicamentoState.Error(
                    it.message ?: "Erro desconhecido"
                )
            )
        }
        return medicamentosLiveData
    }

    fun obterMedicamento(documentId: String) {
        _medicamentoState.value = MedicamentoState.Loading
        medicamentoRepository.getMedicamento(documentId).addOnSuccessListener {
            try {

            } catch (_: FirebaseFirestoreException) {

            }
        }.addOnFailureListener {
            // Todo erro
        }
    }

    fun obterMedicamentoPorId(id: String): Medicamento? {
        return medicamentosLiveData.value?.find { it.id == id }
    }


    /**
     * Função para popular o banco de dados com dados ficticios
     */
    fun gerarDadosFalsos() {
        val fake = Faker()
        val nomesMedicamentos = listOf(
            "Paracetamol", "Ibuprofeno", "Aspirina", "Omeprazol", "Dipirona", "Amoxicilina",
            "Cefalexina", "Azitromicina", "Clonazepam", "Losartana"
        )
        val dosagens = listOf("${Random.nextInt(1, 10)}", "${Random.nextInt(20, 100)}")
        viewModelScope.launch {
            for (i in 1..20) {
                medicamentoRepository.createMedicamento(
                    Medicamento(
                        nome = nomesMedicamentos.random(),
                        dosagem = fake.random.randomValue(dosagens),
                        descricao = fake.lorem.supplemental(),
                        tipoDosagem = fake.random.randomValue(ListsCadastroMedicamentos.TiposDosagem),
                        frequencia = "${Random.nextInt(1, 4)} vezes ao dia",
                        intervalo = Random.nextInt(1, 12),
                        horario = listOf(
                            "${Random.nextInt(0, 12)}:${Random.nextInt(0, 59)}",
                            "${Random.nextInt(13, 23)}:${Random.nextInt(0, 59)}"
                        )

                    )
                )
            }
        }
    }
}

sealed class MedicamentoState {
    object Done : MedicamentoState()
    object Init : MedicamentoState()
    object Launched : MedicamentoState()

    //    object Unauthenticated : AuthState()
    object Loading : MedicamentoState()
    data class Error(val message: String) : MedicamentoState()
}