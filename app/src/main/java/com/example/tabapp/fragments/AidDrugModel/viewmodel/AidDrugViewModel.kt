package com.example.tabapp.fragments.AidDrugModel.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tabapp.data.AidDrugDatabase
import com.example.tabapp.fragments.AidDrugModel.AidDrug
import com.example.tabapp.fragments.AidDrugModel.repository.AidDrugRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AidDrugViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<AidDrug>>
    private val repository: AidDrugRepository

    init {
        val aiddrugDao = AidDrugDatabase.getDatabase(application).aiddrugDao()
        repository = AidDrugRepository(aiddrugDao)
        readAllData = repository.readAllData
    }

    fun addAidDrug(aiddrug: AidDrug) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAidDrug(aiddrug)
        }
    }

    fun updateAidDrug(aiddrug: AidDrug) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAidDrug(aiddrug)
        }
    }

    fun deleteAidDrug(aiddrug: AidDrug) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAidDrug(aiddrug)
        }
    }

    fun deleteAllAidDrugs() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllAidDrugs()
        }
    }
}