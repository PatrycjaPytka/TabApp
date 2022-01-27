package com.example.tabapp.fragments.AidDrugModel.repository

import androidx.lifecycle.LiveData
import com.example.tabapp.data.AidDrugDao
import com.example.tabapp.fragments.AidDrugModel.AidDrug


class AidDrugRepository(private val aiddrugDao: AidDrugDao) {

    val readAllData: LiveData<List<AidDrug>> = aiddrugDao.readAllData()

    suspend fun addAidDrug(aiddrug: AidDrug) {
        aiddrugDao.addAidDrug(aiddrug)
    }

    suspend fun updateAidDrug(aiddrug: AidDrug) {
        aiddrugDao.updateAidDrug(aiddrug)
    }

    suspend fun deleteAidDrug(aiddrug: AidDrug) {
        aiddrugDao.deleteAidDrug(aiddrug)
    }

    suspend fun deleteAllAidDrugs() {
        aiddrugDao.deleteAllAidDrugs()
    }
}