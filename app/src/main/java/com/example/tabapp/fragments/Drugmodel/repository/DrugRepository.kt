package com.example.tabapp.fragments.Drugmodel.repository

import androidx.lifecycle.LiveData
import com.example.tabapp.data.DrugDao
import com.example.tabapp.fragments.Drugmodel.Drug

class DrugRepository(private val drugDao: DrugDao) {

    val readAllData: LiveData<List<Drug>> = drugDao.readAllData()

    suspend fun addDrug(drug: Drug) {
        drugDao.addDrug(drug)
    }

    suspend fun updateDrug(drug: Drug) {
        drugDao.updateDrug(drug)
    }

    suspend fun deleteDrug(drug: Drug) {
        drugDao.deleteDrug(drug)
    }

    suspend fun deleteAllDrugs() {
        drugDao.deleteAllDrugs()
    }
}