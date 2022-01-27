package com.example.tabapp.fragments.Drugmodel.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tabapp.data.DrugDatabase
import com.example.tabapp.fragments.Drugmodel.repository.DrugRepository
import com.example.tabapp.fragments.Drugmodel.Drug
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DrugViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Drug>>
    private val repository: DrugRepository

    init {
        val drugDao = DrugDatabase.getDatabase(application).drugDao()
        repository = DrugRepository(drugDao)
        readAllData = repository.readAllData
    }

    fun addDrug(drug: Drug) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDrug(drug)
        }
    }

    fun updateDrug(drug: Drug) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDrug(drug)
        }
    }

    fun deleteDrug(drug: Drug) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteDrug(drug)
        }
    }

    fun deleteAllDrugs() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllDrugs()
        }
    }
}