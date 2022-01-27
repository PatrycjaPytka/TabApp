package com.example.tabapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabapp.fragments.Drugmodel.Drug

@Dao
interface DrugDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addDrug(drug: Drug)

    @Query("SELECT * FROM drug_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Drug>>

    @Update
    fun updateDrug(drug: Drug)

    @Delete
    fun deleteDrug(drug: Drug)

    @Query("DELETE FROM drug_table")
    fun deleteAllDrugs()
}