package com.digitaldose.medtime.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitaldose.medtime.models.Medicamento
import com.digitaldose.medtime.repositories.MedicamentoRepository
import com.google.firebase.firestore.FirebaseFirestore
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
    private val _medicamentosLiveData = MutableLiveData<List<Medicamento>>()
    val medicamentosLiveData: LiveData<List<Medicamento>> = _medicamentosLiveData

    init {
        _medicamentoState.value = MedicamentoState.Init
    }

    fun salvarMedicamento(medicamento: Medicamento) {
        _medicamentoState.value = MedicamentoState.Loading
        medicamentoRepository.createMedicamento(medicamento).addOnSuccessListener {
            _medicamentoState.value = MedicamentoState.Done
        }.addOnFailureListener {
            _medicamentoState.value = MedicamentoState.Error(it.message ?: "Erro desconhecido")
        }
    }

    fun atualizarMedicamento(medicamento: Medicamento) {
        _medicamentoState.value = MedicamentoState.Loading
        medicamentoRepository.updateMedicamento(medicamento).addOnSuccessListener {
            _medicamentoState.value = MedicamentoState.Done
        }.addOnFailureListener { exception ->
            _medicamentoState.value = MedicamentoState.Error(exception.message ?: "Erro desconhecido")
        }
    }

    fun deletarMedicamento(documentId: String) {
        _medicamentoState.value = MedicamentoState.Loading
        medicamentoRepository.deleteMedicamento(documentId).addOnSuccessListener {
            _medicamentoState.value = MedicamentoState.Done
        }.addOnFailureListener {
            _medicamentoState.value = MedicamentoState.Error(it.message ?: "Erro desconhecido")
        }
    }

    fun obterMedicamentos() {
        _medicamentoState.postValue(MedicamentoState.Loading)
        medicamentoRepository.getMedicamentos().addOnSuccessListener {
            try {
                val medicamentos: MutableList<Medicamento> = mutableListOf()
                for (item in it.documents) {
                    val medicamento = Medicamento()
                    medicamento.nome = item.data!!["nome"] as String?
                    medicamento.descricao = item.data!!["descricao"] as String?
                    medicamento.dosagem = item.data!!["dosagem"] as String?
                    medicamento.frequencia = item.data!!["frequencia"] as String?
                    medicamento.horario = item.data!!["horario"] as List<String>?
                    medicamentos.add(medicamento)
                }
                _medicamentosLiveData.postValue(medicamentos)
                _medicamentoState.value = MedicamentoState.Done
            } catch (e: FirebaseFirestoreException) {
                _medicamentoState.postValue(MedicamentoState.Error(e.message ?: "Erro desconhecido"))
            }

        }.addOnFailureListener {
            _medicamentoState.postValue(MedicamentoState.Error(it.message ?: "Erro desconhecido"))
        }
    }

    fun obterMedicamento(documentId: String) {
        _medicamentoState.value = MedicamentoState.Loading
        medicamentoRepository.getMedicamento(documentId).addOnSuccessListener{
            try {

            } catch (e: FirebaseFirestoreException) {

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
        val dosagens = listOf("${Random.nextInt(20, 100)} mg", "${Random.nextInt(20, 100)} ml")
        viewModelScope.launch {
            for (i in 1..20) {
                medicamentoRepository.createMedicamento(
                    Medicamento(
                        nome = nomesMedicamentos.random(),
                        dosagem = fake.random.randomValue(dosagens),
                        descricao = fake.lorem.supplemental(),
                        frequencia = "${Random.nextInt(1, 4)} vezes ao dia",
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
    //    object Unauthenticated : AuthState()
    object Loading : MedicamentoState()
    data class Error(val message: String) : MedicamentoState()
}