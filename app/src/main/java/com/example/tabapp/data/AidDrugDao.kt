package com.example.tabapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabapp.fragments.AidDrugModel.AidDrug

@Dao
interface AidDrugDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAidDrug(aidDrug: AidDrug)

    @Query("SELECT * FROM aid_drug_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<AidDrug>>

    @Update
    fun updateAidDrug(aidDrug: AidDrug)

    @Delete
    fun deleteAidDrug(aidDrug: AidDrug)

    @Query("DELETE FROM aid_drug_table")
    fun deleteAllAidDrugs()
}