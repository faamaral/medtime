package com.digitaldose.medtime.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitaldose.medtime.database.models.Lembrete
import com.digitaldose.medtime.database.models.LembreteEntity
import com.digitaldose.medtime.database.repositories.LembreteRepository
import kotlinx.coroutines.launch

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 21/03/2025
 */
class LembreteViewModel(
    private val lembreteRepository: LembreteRepository
): ViewModel() {
//    private val _lembreteLiveData = MutableLiveData<MutableList<LembreteEntity>>()
    val lembreteLiveData: LiveData<MutableList<LembreteEntity>> = lembreteRepository.getAllLembretes()
    val lembreteTodayLiveData: LiveData<MutableList<LembreteEntity>> = lembreteRepository.getAllLembretesToday()

//    init {
//        getLembretes()
//    }
//
//    fun getLembretes() {
//        viewModelScope.launch {
//            val lembretes = lembreteRepository.getAllLembretes().value
//            _lembreteLiveData.postValue(lembretes ?: mutableListOf())
//        }
//    }
    fun atualizarLembrete(lembrete: LembreteEntity) {
        viewModelScope.launch {
            lembreteRepository.updateLembrete(lembrete)
        }
    }
}